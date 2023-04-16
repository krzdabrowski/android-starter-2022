package eu.krzdabrowski.starter.basicfeature.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.krzdabrowski.starter.basicfeature.data.remote.api.RocketApi
import eu.krzdabrowski.starter.basicfeature.data.repository.RocketRepositoryImpl
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketRepository
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.getRockets
import eu.krzdabrowski.starter.basicfeature.domain.usecase.refreshRockets
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RocketModule {

    @Provides
    @Singleton
    fun provideRocketApi(
        retrofit: Retrofit,
    ): RocketApi {
        return retrofit.create(RocketApi::class.java)
    }

    @Provides
    fun provideGetRocketsUseCase(
        rocketRepository: RocketRepository,
    ): GetRocketsUseCase {
        return GetRocketsUseCase {
            getRockets(rocketRepository)
        }
    }

    @Provides
    fun provideRefreshRocketsUseCase(
        rocketRepository: RocketRepository,
    ): RefreshRocketsUseCase {
        return RefreshRocketsUseCase {
            refreshRockets(rocketRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindRocketRepository(impl: RocketRepositoryImpl): RocketRepository
    }
}
