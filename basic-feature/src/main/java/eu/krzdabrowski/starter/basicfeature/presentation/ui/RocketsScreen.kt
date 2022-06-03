package eu.krzdabrowski.starter.basicfeature.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsViewModel
import eu.krzdabrowski.starter.core.extensions.getLifecycleAwareUiState

@Composable
fun RocketsScreen(
    viewModel: RocketsViewModel = hiltViewModel()
) {
    val uiState by getLifecycleAwareUiState(viewModel)

    Text("Hello rockets")
}
