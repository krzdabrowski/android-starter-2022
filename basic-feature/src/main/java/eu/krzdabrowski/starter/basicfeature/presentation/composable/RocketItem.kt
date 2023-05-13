package eu.krzdabrowski.starter.basicfeature.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import eu.krzdabrowski.starter.basicfeature.R
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import eu.krzdabrowski.starter.core.design.Typography

@Composable
fun RocketItem(
    rocket: RocketDisplayable,
    modifier: Modifier = Modifier,
    onRocketClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.dimen_medium),
            )
            .clickable { onRocketClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.dimen_small),
            ),
        ) {
            Text(
                text = rocket.name,
                style = Typography.titleMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.rocket_cost_per_launch,
                    rocket.costPerLaunchInMillions,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.rocket_first_flight,
                    rocket.firstFlightDate,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.rocket_height,
                    rocket.heightInMeters,
                ),
                style = Typography.bodyMedium,
            )

            Text(
                text = stringResource(
                    id = R.string.rocket_weight,
                    rocket.weightInTonnes,
                ),
                style = Typography.bodyMedium,
            )
        }

        AsyncImage(
            model = rocket.imageUrl,
            contentDescription = stringResource(id = R.string.rocket_image_content_description),
            modifier = Modifier
                .weight(1f),
        )
    }
}
