package eu.krzdabrowski.starter.basicfeature.domain.usecase

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketRepository
import eu.krzdabrowski.starter.core.extensions.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun interface GetRocketsUseCase : () -> Flow<Result<List<Rocket>>>

fun getRockets(
    rocketRepository: RocketRepository
): Flow<Result<List<Rocket>>> = rocketRepository
    .getRockets()
    .map {
        resultOf { it }
    }
