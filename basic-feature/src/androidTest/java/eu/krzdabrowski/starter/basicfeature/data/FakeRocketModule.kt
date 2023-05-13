package eu.krzdabrowski.starter.basicfeature.data

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import eu.krzdabrowski.starter.basicfeature.data.di.RocketModule
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.core.utils.resultOf
import kotlinx.coroutines.flow.flowOf

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RocketModule::class],
)
internal object FakeRocketModule {

    @Provides
    fun provideFakeGetRocketsUseCase(): GetRocketsUseCase {
        return GetRocketsUseCase {
            flowOf(
                Result.success(generateTestRocketsFromDomain()),
            )
        }
    }

    @Provides
    fun provideNoopRefreshRocketsUseCase(): RefreshRocketsUseCase {
        return RefreshRocketsUseCase {
            resultOf { }
        }
    }
}
