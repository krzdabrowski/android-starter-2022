package eu.krzdabrowski.starter.core.navigation

import eu.krzdabrowski.starter.core.coroutines.MainImmediateScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor(
    @MainImmediateScope private val externalMainImmediateScope: CoroutineScope,
) {
    private val navigationCommandChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    val navigationEvent = navigationCommandChannel.receiveAsFlow()

    fun navigate(command: NavigationCommand) {
        externalMainImmediateScope.launch {
            navigationCommandChannel.send(command)
        }
    }

    fun navigateBack() {
        externalMainImmediateScope.launch {
            navigationCommandChannel.send(object : NavigationCommand {
                override val destination: String = NavigationDestination.Back.route
            })
        }
    }
}
