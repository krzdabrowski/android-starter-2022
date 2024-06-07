package eu.krzdabrowski.starter.basicfeature.presentation.composable.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import eu.krzdabrowski.starter.basicfeature.R
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import eu.krzdabrowski.starter.core.design.Typography

@Composable
fun RocketDetailsContent(rocketItem: RocketDisplayable) {
    Column(modifier = Modifier.fillMaxSize()) {

        // set rocket title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = rocketItem.name,
            color = Color.Black,
            style = Typography.headlineLarge,
        )
        // rocket image
        AsyncImage(
            model = rocketItem.imageUrl,
            contentDescription = stringResource(id = R.string.rocket_image_content_description),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit,
        )
    }
}