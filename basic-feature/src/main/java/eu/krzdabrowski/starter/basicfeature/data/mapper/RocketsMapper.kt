package eu.krzdabrowski.starter.basicfeature.data.mapper

import eu.krzdabrowski.starter.basicfeature.data.remote.model.RocketResponse
import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import java.time.LocalDate

fun RocketResponse.toDomainModel() = Rocket(
    name = name,
    costPerLaunch = costPerLaunch,
    firstFlight = LocalDate.parse(firstFlightDate),
    height = height.meters.toInt(),
    weight = weight.kg,
    wikiUrl = wikiUrl,
    imageUrl = imageUrls.random()
)
