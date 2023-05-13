package eu.krzdabrowski.starter.basicfeature.domain.usecase

import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketRepository
import eu.krzdabrowski.starter.core.utils.resultOf

fun interface RefreshRocketsUseCase : suspend () -> Result<Unit>

suspend fun refreshRockets(
    rocketRepository: RocketRepository,
): Result<Unit> = resultOf {
    rocketRepository.refreshRockets()
}
