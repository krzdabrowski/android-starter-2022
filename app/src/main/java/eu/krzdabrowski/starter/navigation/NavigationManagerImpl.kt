package eu.krzdabrowski.starter.navigation

import eu.krzdabrowski.starter.core.di.MainScope
import eu.krzdabrowski.starter.core.navigation.NavigationCommand
import eu.krzdabrowski.starter.core.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Discussion: https://proandroiddev.com/architecture-in-jetpack-compose-mvp-mvvm-mvi-17d8170a13fd
// And: https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
// And: https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
class NavigationManagerImpl @Inject constructor(
    @MainScope private val externalMainScope: CoroutineScope
) : NavigationManager {
    private val navigationCommandChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    override val navigationEvent = navigationCommandChannel.receiveAsFlow()

    override fun navigate(command: NavigationCommand) {
        externalMainScope.launch {
            navigationCommandChannel.send(command)
        }
    }
}
