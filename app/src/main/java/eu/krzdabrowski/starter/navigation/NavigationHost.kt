package eu.krzdabrowski.starter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import eu.krzdabrowski.starter.core.navigation.NavigationDestination
import eu.krzdabrowski.starter.core.navigation.NavigationFactory

@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Rockets.route
    ) {
        factories.forEach {
            it.create(this)
        }
    }
}
