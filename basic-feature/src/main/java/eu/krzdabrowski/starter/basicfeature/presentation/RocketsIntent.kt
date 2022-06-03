package eu.krzdabrowski.starter.basicfeature.presentation

sealed class RocketsIntent {
    object GetRockets : RocketsIntent()
    data class NavigateToRocketDetails(val id: String) : RocketsIntent()
}
