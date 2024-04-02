package eu.krzdabrowski.starter.core.presentation.mvi

import eu.krzdabrowski.starter.core.coroutines.flatMapConcurrently
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onSubscription

class IntentDelegateImpl<INTENT, PARTIAL_UI_STATE> : IntentDelegate<INTENT, PARTIAL_UI_STATE> {

    private val intentsFlowListenerStarted = CompletableDeferred<Unit>()
    private val intentsFlow = MutableSharedFlow<INTENT>()

    override fun getIntents(
        mapOperation: (INTENT) -> Flow<PARTIAL_UI_STATE>,
    ): Flow<PARTIAL_UI_STATE> = intentsFlow
        .onSubscription { intentsFlowListenerStarted.complete(Unit) }
        .flatMapConcurrently(
            transform = mapOperation,
        )

    override suspend fun setIntent(intent: INTENT) {
        intentsFlowListenerStarted.await()
        intentsFlow.emit(intent)
    }
}
