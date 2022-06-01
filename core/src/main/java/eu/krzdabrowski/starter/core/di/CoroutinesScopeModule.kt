package eu.krzdabrowski.starter.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention
@Qualifier
annotation class MainScope // UI-related

@Retention
@Qualifier
annotation class IoScope // I/O-related

@Retention
@Qualifier
annotation class DefaultScope // CPU-related

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesScopeModule {

    @Singleton
    @Provides
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
        }

    @MainScope
    @Singleton
    @Provides
    fun provideMainScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher + exceptionHandler)

    @IoScope
    @Singleton
    @Provides
    fun provideIoScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher + exceptionHandler)

    @DefaultScope
    @Singleton
    @Provides
    fun provideDefaultScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        exceptionHandler: CoroutineExceptionHandler
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher + exceptionHandler)
}
