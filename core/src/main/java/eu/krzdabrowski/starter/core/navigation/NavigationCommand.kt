package eu.krzdabrowski.starter.core.navigation

import androidx.navigation.NavOptions

// Based on: https://medium.com/google-developer-experts/modular-navigation-with-jetpack-compose-fda9f6b2bef7
// And: https://proandroiddev.com/how-to-make-jetpack-compose-navigation-easier-and-testable-b4b19fd5f2e4
// And: https://funkymuse.dev/posts/compose_hilt_mm/
interface NavigationCommand {
    val destination: String
    val configuration: NavOptions
        get() = NavOptions.Builder().build()
}
