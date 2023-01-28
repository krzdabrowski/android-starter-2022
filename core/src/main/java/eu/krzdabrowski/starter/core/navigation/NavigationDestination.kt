package eu.krzdabrowski.starter.core.navigation

sealed class NavigationDestination(
    val route: String
) {
    object Rockets : NavigationDestination("rocketsDestination")
    object Back : NavigationDestination("navigationBack")
}
