package eu.krzdabrowski.starter.basicfeature.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsViewModel
import eu.krzdabrowski.starter.core.extensions.collectAsStateWithLifecycle
import eu.krzdabrowski.starter.core.extensions.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun RocketsScreen(
    viewModel: RocketsViewModel = hiltViewModel()
) {
    HandleEvents(viewModel.event)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Text("Hello rockets")
}

@Composable
private fun HandleEvents(events: Flow<RocketsEvent>) {
    events.collectWithLifecycle {
        when (it) {
            is RocketsEvent.NavigateToRocketDetailsFailed -> { }
        }
    }
}
