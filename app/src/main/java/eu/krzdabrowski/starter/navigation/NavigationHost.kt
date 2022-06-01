package eu.krzdabrowski.starter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import eu.krzdabrowski.starter.core.navigation.NavigationFactory

@Composable
fun NavigationHost(
    navController: NavHostController,
    factories: Set<NavigationFactory>
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Home
    ) {
        factories.forEach {
            it.create(this)
        }
    }
}
