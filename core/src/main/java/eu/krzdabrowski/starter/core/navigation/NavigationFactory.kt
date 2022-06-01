package eu.krzdabrowski.starter.core.navigation

import androidx.navigation.NavGraphBuilder

// Based on: https://medium.com/bumble-tech/scalable-jetpack-compose-navigation-9c0659f7c912
interface NavigationFactory {
    fun create(builder: NavGraphBuilder)
}
