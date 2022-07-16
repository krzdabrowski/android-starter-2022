package eu.krzdabrowski.starter.basicfeature.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RocketClicked
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

    when {
        uiState.isLoading -> RocketsLoadingContent()

        uiState.isError -> RocketsErrorContent()

        uiState.rockets.isNotEmpty() -> RocketsListContent(
            rocketList = uiState.rockets,
            onRocketClick = {
                viewModel.acceptIntent(RocketClicked(it))
            }
        )
    }
}

@Composable
private fun HandleEvents(events: Flow<RocketsEvent>) {
    val uriHandler = LocalUriHandler.current

    events.collectWithLifecycle {
        when (it) {
            is OpenWebBrowserWithDetails -> {
                uriHandler.openUri(it.uri)
            }
        }
    }
}
