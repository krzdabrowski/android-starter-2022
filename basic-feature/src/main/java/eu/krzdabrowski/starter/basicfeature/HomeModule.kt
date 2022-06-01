package eu.krzdabrowski.starter.basicfeature

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Singleton
    @Binds
    @IntoSet
    fun bindHomeNavigationFactory(factory: HomeNavigationFactory): NavigationFactory
}
