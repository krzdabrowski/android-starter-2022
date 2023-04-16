package eu.krzdabrowski.starter.basicfeature.tests

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import eu.krzdabrowski.starter.basicfeature.R
import eu.krzdabrowski.starter.basicfeature.data.generateTestRocketsFromPresentation
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsUiState
import eu.krzdabrowski.starter.basicfeature.presentation.composable.RocketsScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RocketsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val testRockets = generateTestRocketsFromPresentation()

    private lateinit var rocketContentDescription: String
    private lateinit var errorRefreshingMessage: String
    private lateinit var errorFetchingMessage: String

    @Before
    fun setUp() {
        with(composeTestRule.activity) {
            rocketContentDescription = getString(R.string.rocket_image_content_description)
            errorRefreshingMessage = getString(R.string.rockets_error_refreshing)
            errorFetchingMessage = getString(R.string.rockets_error_fetching)
        }
    }

    @Test
    fun rocketsScreen_whenContentAvailableAndErrorOccurs_shouldKeepContent() {
        setUpComposable(
            RocketsUiState(
                rockets = testRockets,
                isError = true,
            ),
        )

        composeTestRule
            .onAllNodesWithContentDescription(rocketContentDescription)
            .assertCountEquals(testRockets.size)
    }

    @Test
    fun rocketsScreen_whenContentAvailableAndErrorOccurs_shouldShowErrorSnackbar() {
        setUpComposable(
            RocketsUiState(
                rockets = testRockets,
                isError = true,
            ),
        )

        composeTestRule
            .onNodeWithText(errorRefreshingMessage)
            .assertExists()
    }

    @Test
    fun rocketsScreen_whenContentNotAvailableAndErrorOccurs_shouldHaveErrorContent() {
        setUpComposable(
            RocketsUiState(isError = true),
        )

        composeTestRule
            .onNodeWithText(errorFetchingMessage)
            .assertExists()
    }

    private fun setUpComposable(
        rocketsUiState: RocketsUiState,
    ) {
        composeTestRule.setContent {
            RocketsScreen(
                uiState = rocketsUiState,
                onIntent = { },
            )
        }
    }
}
