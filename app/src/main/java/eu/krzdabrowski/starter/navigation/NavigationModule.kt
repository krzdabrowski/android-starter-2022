package eu.krzdabrowski.starter.navigation

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.krzdabrowski.starter.core.di.MainImmediateScope
import eu.krzdabrowski.starter.core.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @Binds
    abstract fun bindNavigationManager(impl: NavigationManagerImpl): NavigationManager

    companion object {
        @Singleton
        @Provides
        fun provideNavigationManager(@MainImmediateScope externalMainImmediateScope: CoroutineScope): NavigationManagerImpl =
            NavigationManagerImpl(externalMainImmediateScope)
    }
}
