package eu.krzdabrowski.starter.basicfeature.domain.repository

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket

interface RocketsRepository {
    suspend fun getRockets(): List<Rocket>
}
