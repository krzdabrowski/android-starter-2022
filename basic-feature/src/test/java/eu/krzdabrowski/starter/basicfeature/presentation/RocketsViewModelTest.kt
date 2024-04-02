package eu.krzdabrowski.starter.basicfeature.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import eu.krzdabrowski.starter.basicfeature.domain.usecase.GetRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.generateTestRocketFromDomain
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsEvent.OpenWebBrowserWithDetails
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RefreshRockets
import eu.krzdabrowski.starter.basicfeature.presentation.RocketsIntent.RocketClicked
import eu.krzdabrowski.starter.basicfeature.presentation.mapper.toPresentationModel
import eu.krzdabrowski.starter.core.utils.MainDispatcherExtension
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RocketsViewModelTest {

    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @RelaxedMockK
    private lateinit var getRocketsUseCase: GetRocketsUseCase

    // there is some issue with mocking functional interface with kotlin.Result(Unit)
    private lateinit var refreshRocketsUseCase: RefreshRocketsUseCase

    @SpyK
    private var savedStateHandle = SavedStateHandle()

    private lateinit var objectUnderTest: RocketsViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        refreshRocketsUseCase = RefreshRocketsUseCase {
            Result.success(Unit)
        }
    }

    @Test
    fun `should show loading state with no error state first during init rockets retrieval`() = runTest {
        // Given
        every { getRocketsUseCase() } returns emptyFlow()
        setUpRocketsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show loading state with no error state during rockets refresh`() = runTest {
        // Given
        every { getRocketsUseCase() } returns emptyFlow()
        setUpRocketsViewModel()

        // When
        objectUnderTest.acceptIntent(RefreshRockets)

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show fetched rockets with no loading & error state during init rockets retrieval success`() = runTest {
        // Given
        val testRocketsFromDomain = listOf(generateTestRocketFromDomain())
        val testRocketsToPresentation = testRocketsFromDomain.map { it.toPresentationModel() }
        every { getRocketsUseCase() } returns flowOf(
            Result.success(testRocketsFromDomain),
        )
        setUpRocketsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertEquals(
                expected = testRocketsToPresentation,
                actual = actualItem.rockets,
            )
            assertFalse(actualItem.isLoading)
            assertFalse(actualItem.isError)
        }
    }

    @Test
    fun `should show error state with no loading state during init rockets retrieval failure`() = runTest {
        // Given
        every { getRocketsUseCase() } returns flowOf(
            Result.failure(IllegalStateException("Test error")),
        )
        setUpRocketsViewModel()

        // When
        // init

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertFalse(actualItem.isLoading)
        }
    }

    @Test
    fun `should show error state with previously fetched rockets during rockets refresh failure`() = runTest {
        // Given
        val testRocketsFromDomain = listOf(generateTestRocketFromDomain())
        val testRocketsToPresentation = testRocketsFromDomain.map { it.toPresentationModel() }
        every { getRocketsUseCase() } returns flowOf(
            Result.success(testRocketsFromDomain),
        )
        refreshRocketsUseCase = RefreshRocketsUseCase {
            Result.failure(IllegalStateException("Test error"))
        }
        setUpRocketsViewModel()

        // When
        objectUnderTest.acceptIntent(RefreshRockets)

        // Then
        objectUnderTest.uiState.test {
            val actualItem = awaitItem()

            assertTrue(actualItem.isError)
            assertEquals(
                expected = testRocketsToPresentation,
                actual = actualItem.rockets,
            )
        }
    }

    @Test
    fun `should open web browser if link has proper prefix`() = runTest {
        // Given
        val testUri = "https://testrocket.com"
        every { getRocketsUseCase() } returns emptyFlow()
        setUpRocketsViewModel()

        // When
        objectUnderTest.acceptIntent(RocketClicked(testUri))

        // Then
        objectUnderTest.getEvents().test {
            assertEquals(
                expected = OpenWebBrowserWithDetails(testUri),
                actual = awaitItem(),
            )
        }
    }

    @Test
    fun `should not open web browser if link is incorrect`() = runTest {
        // Given
        val testUri = "incorrectlink.com"
        every { getRocketsUseCase() } returns emptyFlow()
        setUpRocketsViewModel()

        // When
        objectUnderTest.acceptIntent(RocketClicked(testUri))

        // Then
        objectUnderTest.getEvents().test {
            expectNoEvents()
        }
    }

    private fun setUpRocketsViewModel(
        initialUiState: RocketsUiState = RocketsUiState(),
    ) {
        objectUnderTest = RocketsViewModel(
            getRocketsUseCase,
            refreshRocketsUseCase,
            savedStateHandle,
            initialUiState,
        )
    }
}
