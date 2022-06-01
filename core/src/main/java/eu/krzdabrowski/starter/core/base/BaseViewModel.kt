package eu.krzdabrowski.starter.core.base

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<UI_STATE : Parcelable, UI_EVENT, PARTIAL_UI_STATE, INTENT>(
    val initialState: UI_STATE
) : ViewModel() {
    private val intentFlow = MutableSharedFlow<INTENT>()

    private val uiStateFlow = MutableStateFlow(initialState)
    val uiState = uiStateFlow.asStateFlow()

    private val uiEventChannel = Channel<UI_EVENT>(Channel.BUFFERED)
    val uiEvent = uiEventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            intentFlow
                .flatMapMerge { mapIntents(it) }
                .scan(initialState, ::reduceUiState)
                .catch { Timber.e(it) }
                .collect { state -> uiStateFlow.emit(state) }
        }
    }

    fun acceptIntent(intent: INTENT) =
        viewModelScope.launch {
            intentFlow.emit(intent)
        }

    protected fun publishEvent(event: UI_EVENT) {
        viewModelScope.launch {
            uiEventChannel.send(event)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE
    ): UI_STATE
}
