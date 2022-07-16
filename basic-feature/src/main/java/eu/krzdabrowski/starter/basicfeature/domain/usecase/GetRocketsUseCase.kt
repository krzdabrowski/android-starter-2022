package eu.krzdabrowski.starter.basicfeature.domain.usecase

import eu.krzdabrowski.starter.basicfeature.domain.model.Rocket
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketsRepository
import eu.krzdabrowski.starter.core.extensions.resultOf
import javax.inject.Inject

interface GetRocketsUseCase {
    suspend operator fun invoke(): Result<List<Rocket>>
}

class GetRocketsUseCaseImpl @Inject constructor(
    private val rocketsRepository: RocketsRepository
) : GetRocketsUseCase {

    override suspend fun invoke(): Result<List<Rocket>> = resultOf {
        rocketsRepository.getRockets()
    }
}
