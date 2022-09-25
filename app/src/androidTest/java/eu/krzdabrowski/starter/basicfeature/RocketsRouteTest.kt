package eu.krzdabrowski.starter.basicfeature

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import eu.krzdabrowski.starter.MainActivity
import eu.krzdabrowski.starter.basicfeature.presentation.composable.RocketsRoute
import eu.krzdabrowski.starter.utils.getHiltTestViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RocketsRouteTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            RocketsRoute(
                viewModel = composeTestRule.getHiltTestViewModel()
            )
        }
    }

    @Test
    fun rocketsRoute_happyPath_shouldShowAllFakeRockets() {
        testRocketsFromDomain.forEach { rocket ->
            composeTestRule
                .onNodeWithText(rocket.name)
                .assertExists()
        }
    }
}
