package eu.krzdabrowski.starter.basicfeature.presentation.mapper

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import eu.krzdabrowski.starter.basicfeature.utils.getDateFormatted
import java.time.format.DateTimeFormatter

private const val TONNE = 1_000
private const val MILLION = 1_000_000

fun Rocket.toPresentationModel() = RocketDisplayable(
    id = id,
    name = name,
    costPerLaunchInMillions = costPerLaunch / MILLION,
    firstFlightDate = firstFlight.getDateFormatted(),
    heightInMeters = height,
    weightInTonnes = weight / TONNE,
    wikiUrl = wikiUrl,
    country = country,
    imageUrl = imageUrl,
)
