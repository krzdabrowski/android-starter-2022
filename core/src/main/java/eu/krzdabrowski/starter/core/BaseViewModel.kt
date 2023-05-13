package eu.krzdabrowski.starter.core

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE,
) : ViewModel() {
    private val intentFlow = MutableSharedFlow<INTENT>()
    private val continuousPartialStateFlow = MutableSharedFlow<PARTIAL_UI_STATE>()

    private val intentFlowListenerStarted = CompletableDeferred<Unit>()
    private val continuousPartialStateFlowListenerStarted = CompletableDeferred<Unit>()

    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            merge(
                userIntents(),
                continuousFlows(),
            )
                .scan(uiState.value, ::reduceUiState)
                .catch { Timber.e(it) }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    private fun userIntents(): Flow<PARTIAL_UI_STATE> =
        intentFlow
            .onStart { intentFlowListenerStarted.complete(Unit) }
            .flatMapConcat(::mapIntents)

    private fun continuousFlows(): Flow<PARTIAL_UI_STATE> =
        continuousPartialStateFlow
            .onStart { continuousPartialStateFlowListenerStarted.complete(Unit) }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentFlowListenerStarted.await()
            intentFlow.emit(intent)
        }
    }

    protected fun observeContinuousChanges(changesFlow: Flow<PARTIAL_UI_STATE>) {
        viewModelScope.launch {
            continuousPartialStateFlowListenerStarted.await()
            continuousPartialStateFlow.emitAll(changesFlow)
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
