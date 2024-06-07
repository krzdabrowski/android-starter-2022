package eu.krzdabrowski.starter.basicfeature.presentation.composable.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.krzdabrowski.starter.basicfeature.presentation.composable.details.RocketDetailsScreen
import eu.krzdabrowski.starter.core.navigation.NavigationDestination
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import javax.inject.Inject

class RocketDetailsNavigationFactory @Inject constructor() : NavigationFactory {
    override fun create(builder: NavGraphBuilder) {
        builder.composable(NavigationDestination.RocketDetails.route) {
            RocketDetailsScreen()
        }
    }
}
