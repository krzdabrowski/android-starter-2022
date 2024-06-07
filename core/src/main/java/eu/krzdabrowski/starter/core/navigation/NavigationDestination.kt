package eu.krzdabrowski.starter.core.navigation

sealed class NavigationDestination(
    val route: String,
) {
    companion object {
        const val BACK = "navigationBack"
        const val ROCKETS = "rocketsDestination"
        const val ROCKET_DETAILS = "rocketDetailsDestination/{rocketId}"
    }

    data object Rockets : NavigationDestination(ROCKETS)
    data object RocketDetails : NavigationDestination(ROCKET_DETAILS)
    data object Back : NavigationDestination(BACK)
}
