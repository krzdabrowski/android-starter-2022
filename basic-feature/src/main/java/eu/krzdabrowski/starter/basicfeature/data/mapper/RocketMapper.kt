package eu.krzdabrowski.starter.basicfeature.data.mapper

import eu.krzdabrowski.starter.basicfeature.data.local.model.RocketCached
import eu.krzdabrowski.starter.basicfeature.data.remote.model.RocketResponse
import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.utils.getDateFormatted
import eu.krzdabrowski.starter.basicfeature.utils.toLocalDate
import java.time.LocalDate

fun RocketResponse.toDomainModel() = Rocket(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = LocalDate.parse(firstFlightDate),
    height = height.meters.toInt(),
    weight = weight.kg,
    wikiUrl = wikiUrl,
    country = country,
    imageUrl = imageUrls.random(),
)

fun RocketCached.toDomainModel() = Rocket(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = firstFlightDate.toLocalDate(),
    height = height,
    weight = weight,
    wikiUrl = wikiUrl,
    country = country,
    imageUrl = imageUrl,
)

fun Rocket.toEntityModel() = RocketCached(
    id = id,
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlightDate = firstFlight.getDateFormatted(),
    height = height,
    weight = weight,
    wikiUrl = wikiUrl,
    country = country,
    imageUrl = imageUrl,
)
