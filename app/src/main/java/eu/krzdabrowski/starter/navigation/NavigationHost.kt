package eu.krzdabrowski.starter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import eu.krzdabrowski.starter.core.navigation.NavigationDestination
import eu.krzdabrowski.starter.core.navigation.NavigationFactory

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    factories: Set<NavigationFactory>
) {
    NavHost(
        navController = navController,
        startDestination = NavigationDestination.Rockets.route,
        modifier = modifier,
    ) {
        factories.forEach {
            it.create(this)
        }
    }
}
