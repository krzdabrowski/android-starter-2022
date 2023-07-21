package eu.krzdabrowski.starter.core.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Semaphore

// value taken from Channel.Factory val for JVM (internal in Coroutines library)
private const val CHANNEL_DEFAULT_CAPACITY = 64

/**
 * Source: https://github.com/Kotlin/kotlinx.coroutines/compare/master...lowasser:map-concurrent
 * Also modified from concurrent [map] operator to concurrent [flatMap] with some minor enhancements.
 *
 * More parallel examples can be found in [this](https://github.com/Kotlin/kotlinx.coroutines/issues/1147) issue.
 * To be used unless Kotlin Coroutines team would adapt it to the library (issue opened since 2019).
 *
 *
 * Returns a flow with elements equal to `flatMap(transform)`, including having the same order, but evaluates up
 * to `maxConcurrency` of the flows concurrently, up to a limit of `buffer` flows ahead of the consumer.
 *
 * For example, `flowOf(a, b, c, d, e).flatMapConcurrently(maxConcurrency = 3, buffer = 4, transform)` will
 * evaluate `transform(a)`, `transform(b)`, and `transform(c)` concurrently, and will start evaluating `transform(d)`
 * as soon as one of those complete, but `transform(e)` will not start until `transform(a)` is collected.
 *
 * If `x` is emitted by the backing flow and `transform(x)` throws an exception, the returned flow
 * will attempt to cancel the evaluation of `transform` on any values emitted after `x`, but will continue
 * evaluating `transform` on values emitted before `x`.  So in the above example, if `transform(b)` throws before
 * `transform(a)` or `transform(c)` complete, `transform(c)` will be cancelled but `transform(a)` will be allowed to complete.
 */
@Suppress("LoopWithTooManyJumpStatements", "TooGenericExceptionCaught")
internal fun <T, R> Flow<T>.flatMapConcurrently(
    maxConcurrency: Int = DEFAULT_CONCURRENCY,
    buffer: Int = CHANNEL_DEFAULT_CAPACITY,
    transform: suspend (T) -> Flow<R>,
): Flow<R> {
    require(maxConcurrency > 0) { "Expected maxConcurrency to be > 0 but was $maxConcurrency" }
    require(buffer > 1) { "Expected buffer to be > 1 but was $buffer" }

    return flow {
        /*
         * This has lots of moving parts, unfortunately, so here's a sketch of what's going on.
         *
         * First, the semaphore controls concurrency on evaluating transform *and* on getting upstream elements.
         * So in a flow emitting a, b, c with maxConcurrency = 3, there can be three concurrent tasks:
         * computing transform(a) and transform(b) and getting c from upstream. Thus, we acquire permits *before*
         * collecting the element they're associated with, including before the first element,
         * and release them after computing transform on that element.
         *
         * The relationship between collecting the upstream flow and launching the downstream results
         * is unusual, because an exception in transform should cancel its "parent", the upstream flow
         * collection, but not all of its siblings, since prior elements may still be in progress.
         * Normally arranged coroutine scopes simply won't permit that, so we have to make the upstream
         * flow collection a sibling task of evaluating transform on its elements, requiring a separate channel
         * and collection job.
         *
         * To achieve the effect that transform(x) cancels transform on values that come after x, but not values
         * that came before, we maintain an implicit linked list of CompletableDeferreds.
         * exceptionWasThrownEarlier completes exceptionally if transform threw on any element
         * "before this one," and exceptionWasThrownEarlierOrHere completes exceptionally if transform threw
         * on any element before this one, or on this one; we install completion handlers that propagate
         * appropriately.
         */
        val semaphore = Semaphore(permits = maxConcurrency, acquiredPermits = 1)

        supervisorScope {
            val channel = Channel<T>(0)
            val upstreamJob = launch {
                val upstreamCollectExceptionOrNull = runCatching {
                    collect {
                        channel.send(it)
                        semaphore.acquire()
                    }
                }.exceptionOrNull()
                channel.close(upstreamCollectExceptionOrNull)
            }

            var exceptionWasThrownEarlier = CompletableDeferred<Nothing>()
            while (true) {
                val dataResult = try {
                    select<ChannelResult<T>> {
                        channel.onReceiveCatching { it }
                        exceptionWasThrownEarlier.onAwait { it } // throws the exception
                    }
                } catch (thrown: Throwable) {
                    upstreamJob.cancel(thrown.asCancellation())
                    break
                }
                if (dataResult.isClosed) {
                    val ex = dataResult.exceptionOrNull()
                    if (ex != null) {
                        emit(async { throw ex })
                    }
                    break
                }
                val data = dataResult.getOrThrow()

                // Deferred that will be completed exceptionally if evaluating transform on any value before t, or
                // on t itself, threw.
                val exceptionWasThrownEarlierOrHere = CompletableDeferred<Nothing>()

                val evalTransform = async { transform(data) }
                evalTransform.invokeOnCompletion { thrown ->
                    if (thrown != null) {
                        exceptionWasThrownEarlierOrHere.completeExceptionally(thrown)
                    } else {
                        semaphore.release()
                    }
                }
                exceptionWasThrownEarlier.invokeOnCompletion { thrown -> // should never be null
                    // don't nest CancellationExceptions arbitrarily deep
                    evalTransform.cancel(thrown!!.asCancellation())

                    // it's possible that evalTransform completed successfully, but there are other downstream transform's to
                    // cancel, so we can't depend on the evalTransform completion handler to propagate thrown
                    exceptionWasThrownEarlierOrHere.completeExceptionally(thrown)
                }
                emit(evalTransform)
                exceptionWasThrownEarlier = exceptionWasThrownEarlierOrHere
            }
        }
    }
        // one async can be started but unbuffered, and one can be awaiting; the -2 is necessary to
        // ensure exactly what the doc describes (quote from Roman Elizarov):
        //
        // "It is easy to remember. Without buffer there is no concurrency - at most 1 operation is executed at any time,
        // with buffer(0) at most 2 can run concurrently, so with buffer(n) at most n + 2."
        .buffer(if (buffer == Int.MAX_VALUE) buffer else buffer - 2)
        .flatMapConcat { it.await() }
}

private fun Throwable.asCancellation(): CancellationException =
    this as? CancellationException ?: CancellationException(null, this)
