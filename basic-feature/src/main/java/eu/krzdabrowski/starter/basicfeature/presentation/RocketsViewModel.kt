package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.GetRockets
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RocketClicked
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState.Error
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState.Fetched
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState.PartialState.Loading
import eu.krzdabrowski.starter.basicfeature.presentation.mapper.toPresentationModel
import eu.krzdabrowski.starter.core.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val HTTP_PREFIX = "http"
private const val HTTPS_PREFIX = "https"

@HiltViewModel
class RocketsViewModel @Inject constructor(
    private val getRocketsUseCase: GetRocketsUseCase,
    savedStateHandle: SavedStateHandle,
    rocketsInitialState: RocketsUiState
) : BaseViewModel<RocketsUiState, PartialState, RocketsEvent, RocketsIntent>(
    savedStateHandle,
    rocketsInitialState
) {
    init {
        acceptIntent(GetRockets)
    }

    override fun mapIntents(intent: RocketsIntent): Flow<PartialState> = when (intent) {
        is GetRockets -> getRockets()
        is RocketClicked -> rocketClicked(intent.uri)
    }

    override fun reduceUiState(
        previousState: RocketsUiState,
        partialState: PartialState
    ): RocketsUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            rockets = emptyList(),
            isError = false
        )
        is Fetched -> previousState.copy(
            isLoading = false,
            rockets = partialState.list,
            isError = false
        )
        is Error -> previousState.copy(
            isLoading = false,
            rockets = emptyList(),
            isError = true
        )
    }

    private fun getRockets(): Flow<PartialState> = flow {
        emit(Loading)

        getRocketsUseCase()
            .onSuccess { rocketList ->
                emit(Fetched(rocketList.map { it.toPresentationModel() }))
            }
            .onFailure {
                emit(Error(it))
            }
    }

    private fun rocketClicked(uri: String): Flow<PartialState> {
        if (uri.startsWith(HTTP_PREFIX) || uri.startsWith(HTTPS_PREFIX)) {
            publishEvent(OpenWebBrowserWithDetails(uri))
        }

        return emptyFlow()
    }
}
