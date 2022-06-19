package eu.krzdabrowski.starter.basicfeature.presentation

sealed class RocketsEvent {
    data class NavigateToRocketDetailsFailed(val id: String): RocketsEvent()
}
