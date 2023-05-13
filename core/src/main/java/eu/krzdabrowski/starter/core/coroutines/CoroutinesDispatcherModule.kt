package eu.krzdabrowski.starter.core.coroutines

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class MainImmediateDispatcher // UI-related

@Retention
@Qualifier
annotation class IoDispatcher // I/O-related

@Retention
@Qualifier
annotation class DefaultDispatcher // CPU-related

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutinesDispatcherModule {

    @MainImmediateDispatcher
    @Singleton
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
