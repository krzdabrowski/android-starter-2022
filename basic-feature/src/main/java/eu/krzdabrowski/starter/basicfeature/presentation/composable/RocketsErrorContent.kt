package eu.krzdabrowski.starter.basicfeature.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import eu.krzdabrowski.starter.basicfeature.R

@Composable
fun RocketsErrorContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // for swipe-to-refresh
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.rockets_error_fetching),
            color = Color.Red,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )
    }
}
