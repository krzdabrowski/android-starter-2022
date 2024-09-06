package eu.krzdabrowski.starter.core.navigation

import app.cash.turbine.test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class NavigationManagerTest {
    private val testScope = TestScope()

    private lateinit var objectUnderTest: NavigationManager

    @BeforeEach
    fun setUp() {
        setUpNavigationManager()
    }

    @Test
    fun `should preserve navigation command properties`() = testScope.runTest {
        // Given
        val testNavigationCommands = generateTestNavigationCommands(1)
        val testNavigationCommand = testNavigationCommands.first()

        // When
        objectUnderTest.navigate(testNavigationCommand)

        // Then
        objectUnderTest.navigationEvent.test {
            val actualItem = awaitItem()

            assertEquals(
                expected = testNavigationCommand.destination,
                actual = actualItem.destination,
            )

            assertEquals(
                expected = testNavigationCommand.configuration,
                actual = actualItem.configuration,
            )
        }
    }

    @Test
    fun `should collect all navigation commands in order`() = testScope.runTest {
        // Given
        val testNavigationCommands = generateTestNavigationCommands(5)

        // When
        for (testCommand in testNavigationCommands) {
            objectUnderTest.navigate(testCommand)
        }

        // Then
        objectUnderTest.navigationEvent.test {
            assertEquals(
                expected = NavigationDestination.Rockets,
                actual = awaitItem().destination,
            )

            assertEquals(
                expected = NavigationDestination.Back,
                actual = awaitItem().destination,
            )

            assertEquals(
                expected = NavigationDestination.Rockets,
                actual = awaitItem().destination,
            )

            assertEquals(
                expected = NavigationDestination.Back,
                actual = awaitItem().destination,
            )

            assertEquals(
                expected = NavigationDestination.Rockets,
                actual = awaitItem().destination,
            )
        }
    }

    @Test
    fun `should emit navigation back command when navigating back`() = testScope.runTest {
        // Given
        val testNavigationBackCommand = object : NavigationCommand {
            override val destination = NavigationDestination.Back
        }

        // When
        objectUnderTest.navigateBack()

        // Then
        objectUnderTest.navigationEvent.test {
            assertEquals(
                expected = testNavigationBackCommand.destination,
                actual = awaitItem().destination,
            )
        }
    }

    private fun generateTestNavigationCommands(number: Int): List<NavigationCommand> = List(number) {
        if (it % 2 == 0) {
            object : NavigationCommand {
                override val destination = NavigationDestination.Rockets
            }
        } else {
            object : NavigationCommand {
                override val destination = NavigationDestination.Back
            }
        }
    }

    private fun setUpNavigationManager() {
        objectUnderTest = NavigationManager(testScope)
    }
}
