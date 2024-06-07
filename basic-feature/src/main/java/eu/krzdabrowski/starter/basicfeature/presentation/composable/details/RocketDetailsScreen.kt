package eu.krzdabrowski.starter.basicfeature.presentation.composable.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import eu.krzdabrowski.starter.basicfeature.presentation.composable.details.components.RocketDetailsContent
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.components.RocketsErrorContent

@Composable
fun RocketDetailsScreen(
    viewModel: RocketDetailsViewModel = hiltViewModel()
) {

    // init the UI state
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // start screen content
    uiState.rocket?.let { rocket ->
        RocketDetailsContent(rocket)
    }

    if (uiState.showError) {
        RocketsErrorContent()
    }

}