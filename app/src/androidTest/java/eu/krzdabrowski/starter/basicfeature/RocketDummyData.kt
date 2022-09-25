package eu.krzdabrowski.starter.basicfeature

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import java.time.LocalDate

internal val testRocketsFromDomain = listOf(
    Rocket(
        id = "1",
        name = "test rocket",
        costPerLaunch = 10_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 20,
        weight = 30_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/1.jpg"
    ),
    Rocket(
        id = "2",
        name = "test rocket 2",
        costPerLaunch = 20_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 40,
        weight = 50_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/2.jpg"
    ),
    Rocket(
        id = "3",
        name = "test rocket 3",
        costPerLaunch = 30_000_000,
        firstFlight = LocalDate.parse("2022-09-25"),
        height = 60,
        weight = 70_000,
        wikiUrl = "https://testrocket.com",
        imageUrl = "https://testrocket.com/3.jpg"
    )
)
