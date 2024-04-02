package eu.krzdabrowski.starter.core.presentation.mvi

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,
    initialState: UI_STATE,
) : ViewModel(),
    IntentDelegate<INTENT, PARTIAL_UI_STATE> by IntentDelegateImpl(),
    InternalChangesDelegate<PARTIAL_UI_STATE> by InternalChangesDelegateImpl(),
    EventDelegate<EVENT> by EventDelegateImpl() {

    val uiState = savedStateHandle.getStateFlow(
        key = SAVED_UI_STATE_KEY,
        initialValue = initialState,
    )

    init {
        viewModelScope.launch {
            merge(
                getIntents(::mapIntents),
                getInternalChanges(),
            )
                .scan(uiState.value, ::reduceUiState)
                .catch { Timber.e(it) }
                .collect {
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            setIntent(intent)
        }
    }

    protected fun acceptChanges(vararg internalChangesFlows: Flow<PARTIAL_UI_STATE>) {
        viewModelScope.launch {
            setInternalChanges(*internalChangesFlows)
        }
    }

    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE,
    ): UI_STATE
}
