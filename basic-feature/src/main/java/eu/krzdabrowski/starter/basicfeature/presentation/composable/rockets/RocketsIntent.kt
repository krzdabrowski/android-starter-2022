package eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets

sealed class RocketsIntent {
    data object RefreshRockets : RocketsIntent()
    data class RocketClicked(val rocketIndex: Int) : RocketsIntent()
}