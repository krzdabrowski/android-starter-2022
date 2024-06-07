package eu.krzdabrowski.starter.basicfeature.presentation.composable.details

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.presentation.composable.details.RocketDetailsUIState.PartialState
import eu.krzdabrowski.starter.basicfeature.presentation.mapper.toPresentationModel
import eu.krzdabrowski.starter.core.presentation.mvi.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RocketDetailsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
    savedStateHandle: SavedStateHandle,
    rocketsInitialState: RocketDetailsUIState,
) : BaseViewModel<RocketDetailsUIState, PartialState, RocketDetailsEvent, RocketDetailsIntent>(
    savedStateHandle,
    rocketsInitialState,
) {

    init {
        // fetching the rocket id value
        val rocketId = checkNotNull(savedStateHandle["rocketId"]) as String
        // fetching the rocket details
        fetchRocketDetails(rocketId)
    }


    override fun mapIntents(intent: RocketDetailsIntent): Flow<PartialState> =
        when (intent) {
            is RocketDetailsIntent.OnWikiClicked -> openWikiLink()
        }

    override fun reduceUiState(
        previousState: RocketDetailsUIState,
        partialState: PartialState
    ): RocketDetailsUIState =
        when (partialState) {
            is PartialState.Fetched -> previousState.copy(
                rocket = partialState.rocket, showError = false
            )

            PartialState.Failed -> previousState.copy(
                showError = true
            )
        }

    private fun fetchRocketDetails(rocketId: String) = acceptChanges(
        getRocketsUseCase().map { result ->
            result.fold(
                onSuccess = { rocketList ->
                    val rocket = rocketList.find { it.id == rocketId }
                    PartialState.Fetched(rocket?.toPresentationModel())

                },
                onFailure = {
                    PartialState.Failed
                }
            )
        },
    )

    private fun openWikiLink(): Flow<PartialState> = flow {
        // TODO: will handle open wiki
    }
}