package eu.krzdabrowski.starter.basicfeature.presentation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import eu.krzdabrowski.starter.basicfeature.presentation.composable.details.RocketDetailsUIState
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.navigation.RocketsNavigationFactory
import eu.krzdabrowski.starter.basicfeature.presentation.composable.details.navigation.RocketDetailsNavigationFactory
import eu.krzdabrowski.starter.basicfeature.presentation.composable.rockets.RocketsUiState
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object RocketsViewModelModule {

    @Provides
    fun provideInitialRocketsUiState(): RocketsUiState = RocketsUiState()

    @Provides
    fun provideInitialRocketDetailsUIState(): RocketDetailsUIState = RocketDetailsUIState()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface RocketsSingletonModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindRocketsNavigationFactory(factory: RocketsNavigationFactory): NavigationFactory

    @Singleton

    @Binds
    @IntoSet
    fun bindRocketDetailsNavigationFactory(factory: RocketDetailsNavigationFactory): NavigationFactory
}
