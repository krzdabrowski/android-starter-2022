package eu.krzdabrowski.starter.core.navigation

import androidx.navigation.NavOptions

interface NavigationCommand {
    val destination: NavigationDestination
    val configuration: NavOptions
        get() = NavOptions.Builder().build()
}
