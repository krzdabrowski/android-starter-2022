package eu.krzdabrowski.starter.basicfeature.presentation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import eu.krzdabrowski.starter.basicfeature.R
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RefreshRockets
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RocketClicked
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsViewModel
import eu.krzdabrowski.starter.core.extensions.collectWithLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun RocketsRoute(
    viewModel: RocketsViewModel = hiltViewModel()
) {
    HandleEvents(viewModel.event)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RocketsScreen(
        uiState = uiState,
        onRefreshRockets = {
            viewModel.acceptIntent(RefreshRockets)
        },
        onRocketClicked = {
            viewModel.acceptIntent(RocketClicked(it))
        }
    )
}

@Composable
internal fun RocketsScreen(
    uiState: RocketsUiState,
    onRefreshRockets: () -> Unit,
    onRocketClicked: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        // TODO: migrate from accompanist to built-in pull-to-refresh when added to Material3
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = onRefreshRockets,
            modifier = Modifier
                .padding(it)
        ) {
            if (uiState.rockets.isNotEmpty()) {
                RocketsAvailableContent(
                    snackbarHostState = snackbarHostState,
                    uiState = uiState,
                    onRocketClick = onRocketClicked
                )
            } else {
                RocketsNotAvailableContent(
                    uiState = uiState
                )
            }
        }
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

@Composable
private fun RocketsAvailableContent(
    snackbarHostState: SnackbarHostState,
    uiState: RocketsUiState,
    onRocketClick: (String) -> Unit
) {
    if (uiState.isError) {
        val errorMessage = stringResource(R.string.rockets_error_refreshing)

        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                message = errorMessage
            )
        }
    }

    RocketsListContent(
        rocketList = uiState.rockets,
        onRocketClick = { onRocketClick(it) }
    )
}

@Composable
private fun RocketsNotAvailableContent(uiState: RocketsUiState) {
    when {
        uiState.isLoading -> RocketsLoadingPlaceholder()
        uiState.isError -> RocketsErrorContent()
    }
}
