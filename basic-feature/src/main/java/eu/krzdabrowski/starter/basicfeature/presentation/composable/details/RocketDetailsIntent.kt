package eu.krzdabrowski.starter.basicfeature.presentation.composable.details


sealed class RocketDetailsIntent {

    data object OnWikiClicked : RocketDetailsIntent()
}