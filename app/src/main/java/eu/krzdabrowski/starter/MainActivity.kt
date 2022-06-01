package eu.krzdabrowski.starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import eu.krzdabrowski.starter.core.navigation.NavigationFactory
import eu.krzdabrowski.starter.core.navigation.NavigationManager
import eu.krzdabrowski.starter.core.ui.AndroidStarterTheme
import eu.krzdabrowski.starter.navigation.NavigationHost
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var navigationManager: NavigationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStarterTheme {
                val navController = rememberNavController()
                val lifecycle = LocalLifecycleOwner.current.lifecycle

                NavigationHost(
                    navController = navController,
                    factories = navigationFactories
                )

                LaunchedEffect(navController) {
                    navigationManager
                        .navigationEvent
                        .flowWithLifecycle(lifecycle)
                        .collect {
                            navController.navigate(it.destination, it.configuration)
                        }
                }
            }
        }
    }
}
