package eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsRoute
import eu.krzdabrowski.starter.core.navigation.NavigationDestination.Rockets
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import javax.inject.Inject

class RocketsNavigationFactory @Inject constructor() : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(Rockets.route) {
            RocketsRoute()
        }
    }
}
