package eu.krzdabrowski.starter.basicfeature.presentation

sealed class RocketsIntent {
    object GetRockets : RocketsIntent()
    data class RocketClicked(val uri: String) : RocketsIntent()
}
