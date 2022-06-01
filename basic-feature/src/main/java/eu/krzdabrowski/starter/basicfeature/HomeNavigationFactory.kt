package eu.krzdabrowski.starter.basicfeature

import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import eu.krzdabrowski.starter.core.navigation.NavigationManager
import javax.inject.Inject

const val HomeDestination = "homeDestination"

class HomeNavigationFactory @Inject constructor(
    private val navigationManager: NavigationManager
) : NavigationFactory {

    override fun create(builder: NavGraphBuilder) {
        builder.composable(HomeDestination) {
            Text("Hello world")
        }
    }
}
