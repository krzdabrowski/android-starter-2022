package eu.krzdabrowski.starter.basicfeature.presentation

sealed class RocketsUiEvent {
    object GetRocketsFailed : RocketsUiEvent()
    data class NavigateToRocketDetailsFailed(val id: String) : RocketsUiEvent()
}
