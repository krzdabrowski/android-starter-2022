package eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsIntent.RefreshRockets
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsIntent.RocketClicked
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsUiState.PartialState
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsUiState.PartialState.Fetched
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsUiState.PartialState.Loading
import eu.krzdabrowski.starter.basicfeature.presentation.mapper.toPresentationModel
import eu.krzdabrowski.starter.core.navigation.NavigationCommand
import eu.krzdabrowski.starter.core.navigation.NavigationDestination
import eu.krzdabrowski.starter.core.navigation.NavigationManager
import eu.krzdabrowski.starter.core.presentation.mvi.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
    private val refreshRocketsUseCase: RefreshRocketsUseCase,
    private val navigationManager: NavigationManager,
    savedStateHandle: SavedStateHandle,
    rocketsInitialState: RocketsUiState,
) : BaseViewModel<RocketsUiState, PartialState, RocketsEvent, RocketsIntent>(
    savedStateHandle,
    rocketsInitialState,
) {
    init {
        observeRockets()
    }

    override fun mapIntents(intent: RocketsIntent): Flow<PartialState> = when (intent) {
        is RefreshRockets -> refreshRockets()
        is RocketClicked -> rocketClicked(intent.rocketIndex)
    }

    override fun reduceUiState(
        previousState: RocketsUiState,
        partialState: PartialState,
    ): RocketsUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is Fetched -> previousState.copy(
            isLoading = false,
            rockets = partialState.list,
            isError = false,
        )

        is PartialState.Error -> {
            previousState.copy(
                isLoading = false,
                isError = true,
            )
        }

    }

    private fun observeRockets() = acceptChanges(
        getRocketsUseCase().map { result ->
            result.fold(
                onSuccess = { rocketList ->
                    Fetched(rocketList.map { it.toPresentationModel() })
                },
                onFailure = {
                    PartialState.Error(it)
                },
            )
        }.onStart {
            emit(Loading)
        },
    )

    private fun refreshRockets(): Flow<PartialState> =
        flow<PartialState> {
            refreshRocketsUseCase().onFailure {
                emit(PartialState.Error(it))
            }
        }.onStart {
            emit(Loading)
        }

    private fun rocketClicked(rocketIndex: Int): Flow<PartialState> = flow {
        // fetching the selected rocket
        val rocketItem = uiState.value.rockets[rocketIndex]

        // start navigation
        navigationManager.navigate(
            object : NavigationCommand {
                override val destination =
                    NavigationDestination.ROCKET_DETAILS.replaceAfter("/", rocketItem.id)
            }
        )
    }
}