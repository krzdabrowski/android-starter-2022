package eu.krzdabrowski.starter.basicfeature

import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable

internal fun generateTestRocketsFromPresentation() = listOf(
    RocketDisplayable(
        id = "1",
        name = "test rocket",
        costPerLaunchInMillions = 10,
        firstFlightDate = "2022-09-25",
        heightInMeters = 20,
        weightInTonnes = 30,
        wikiUrl = "https://testrocket.com",
        imageUrl = ""
    ),
    RocketDisplayable(
        id = "2",
        name = "test rocket 2",
        costPerLaunchInMillions = 20,
        firstFlightDate = "2022-09-25",
        heightInMeters = 40,
        weightInTonnes = 50,
        wikiUrl = "https://testrocket.com",
        imageUrl = ""
    ),
    RocketDisplayable(
        id = "3",
        name = "test rocket 3",
        costPerLaunchInMillions = 30,
        firstFlightDate = "2022-09-25",
        heightInMeters = 60,
        weightInTonnes = 70,
        wikiUrl = "https://testrocket.com",
        imageUrl = ""
    )
)
