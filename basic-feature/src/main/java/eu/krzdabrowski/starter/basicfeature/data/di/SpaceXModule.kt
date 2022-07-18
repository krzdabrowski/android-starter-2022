package eu.krzdabrowski.starter.basicfeature.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.krzdabrowski.starter.basicfeature.data.remote.api.SpaceXApi
import eu.krzdabrowski.starter.basicfeature.data.repository.RocketsRepositoryImpl
import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketsRepository
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.getRockets
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [SpaceXModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object SpaceXModule {

    @Provides
    @Singleton
    fun provideSpaceXApi(
        retrofit: Retrofit
    ): SpaceXApi {
        return retrofit.create(SpaceXApi::class.java)
    }

    @Provides
    fun provideGetRocketsUseCase(
        rocketsRepository: RocketsRepository
    ): GetRocketsUseCase {
        return {
            getRockets(rocketsRepository)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindRocketsRepository(impl: RocketsRepositoryImpl): RocketsRepository
    }
}
