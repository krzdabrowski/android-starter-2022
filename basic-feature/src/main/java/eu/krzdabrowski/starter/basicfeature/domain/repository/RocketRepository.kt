package eu.krzdabrowski.starter.basicfeature.domain.repository

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketRepository {
    fun getRockets(): Flow<List<Rocket>>
    suspend fun refreshRockets()
}
