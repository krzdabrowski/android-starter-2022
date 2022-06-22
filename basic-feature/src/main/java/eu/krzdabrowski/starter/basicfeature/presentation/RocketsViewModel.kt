package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.GetRockets
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.NavigateToRocketDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState.Dummy
import eu.krzdabrowski.starter.core.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    rocketsInitialState: RocketsUiState
) : BaseViewModel<RocketsUiState, PartialState, RocketsEvent, RocketsIntent>(
    savedStateHandle,
    rocketsInitialState
) {

    override fun mapIntents(intent: RocketsIntent): Flow<PartialState> = when (intent) {
        is GetRockets -> emptyFlow()
        is NavigateToRocketDetails -> emptyFlow()
    }

    override fun reduceUiState(
        previousState: RocketsUiState,
        partialState: PartialState
    ): RocketsUiState = when (partialState) {
        is Dummy -> previousState
    }
}
