package eu.krzdabrowski.starter.basicfeature.data.repository

import eu.krzdabrowski.starter.basicfeature.data.mapper.toDomainModel
import eu.krzdabrowski.starter.basicfeature.data.remote.api.SpaceXApi
import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketsRepository
import javax.inject.Inject

class RocketsRepositoryImpl @Inject constructor(
    private val spaceXApi: SpaceXApi
) : RocketsRepository {

    override suspend fun getRockets(): List<Rocket> = spaceXApi
        .getRockets()
        .map {
            it.toDomainModel()
        }
}
