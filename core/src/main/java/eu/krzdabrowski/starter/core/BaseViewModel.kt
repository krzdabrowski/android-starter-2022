package eu.krzdabrowski.starter.core

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.krzdabrowski.starter.core.coroutines.flatMapConcurrently
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE,
) : ViewModel() {
    private val intentsFlowListenerStarted = CompletableDeferred<Unit>()
    private val changesPartialStateFlowListenerStarted = CompletableDeferred<Unit>()

    private val intentsFlow = MutableSharedFlow<INTENT>()
    private val changesPartialStateFlow = MutableSharedFlow<PARTIAL_UI_STATE>()

    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            merge(
                userIntents(),
                nonUserChanges(),
            )
                .scan(uiState.value, ::reduceUiState)
                .catch { Timber.e(it) }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    private fun userIntents(): Flow<PARTIAL_UI_STATE> =
        intentsFlow
            .onSubscription { intentsFlowListenerStarted.complete(Unit) }
            .flatMapConcurrently(
                transform = ::mapIntents,
            )

    private fun nonUserChanges(): Flow<PARTIAL_UI_STATE> =
        changesPartialStateFlow
            .onSubscription { changesPartialStateFlowListenerStarted.complete(Unit) }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentsFlowListenerStarted.await()
            intentsFlow.emit(intent)
        }
    }

    protected fun acceptChanges(vararg nonUserChangesFlows: Flow<PARTIAL_UI_STATE>) {
        viewModelScope.launch {
            changesPartialStateFlowListenerStarted.await()
            changesPartialStateFlow.emitAll(
                // to flatten Flow with queue behaviour like in userIntents() Flow but without ::mapIntents
                nonUserChangesFlows.asFlow().flatMapConcurrently { it },
            )
        }
    }

    protected fun publishEvent(event: EVENT) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE,
    ): UI_STATE
}
