package eu.krzdabrowski.starter.basicfeature.domain.usecase

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketsRepository
import eu.krzdabrowski.starter.core.extensions.resultOf

typealias GetRocketsUseCase =
    @JvmSuppressWildcards suspend () -> Result<List<Rocket>>

suspend inline fun getRockets(
    rocketsRepository: RocketsRepository
): Result<List<Rocket>> = resultOf {
    rocketsRepository.getRockets()
}
