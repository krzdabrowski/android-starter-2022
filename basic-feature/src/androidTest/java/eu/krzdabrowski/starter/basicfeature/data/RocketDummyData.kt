package eu.krzdabrowski.starter.basicfeature.data

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import java.time.LocalDate

internal fun generateTestRocketsFromPresentation() = listOf(
    RocketDisplayable(
        id = "1",
        name = "test rocket",
        costPerLaunchInMillions = 10,
        firstFlightDate = "2022-09-25",
        heightInMeters = 20,
        weightInTonnes = 30,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
    RocketDisplayable(
        id = "2",
        name = "test rocket 2",
        costPerLaunchInMillions = 20,
        firstFlightDate = "2022-09-25",
        heightInMeters = 40,
        weightInTonnes = 50,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
    RocketDisplayable(
        id = "3",
        name = "test rocket 3",
        costPerLaunchInMillions = 30,
        firstFlightDate = "2022-09-25",
        heightInMeters = 60,
        weightInTonnes = 70,
        wikiUrl = "https://testrocket.com",
        imageUrl = "",
    ),
)

internal fun generateTestRocketsFromDomain() = listOf(
    Rocket(
        id = "1",
        name = "test rocket",
        costPerLaunch = 10_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 20,
        weight = 30_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/1.jpg",
    ),
    Rocket(
        id = "2",
        name = "test rocket 2",
        costPerLaunch = 20_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 40,
        weight = 50_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/2.jpg",
    ),
    Rocket(
        id = "3",
        name = "test rocket 3",
        costPerLaunch = 30_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 60,
        weight = 70_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/3.jpg",
    ),
)
