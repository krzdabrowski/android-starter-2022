package eu.krzdabrowski.starter.basicfeature.domain

import eu.krzdabrowski.starter.basicfeature.domain.repository.RocketRepository
import eu.krzdabrowski.starter.basicfeature.domain.usecase.RefreshRocketsUseCase
import eu.krzdabrowski.starter.basicfeature.domain.usecase.refreshRockets
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class RefreshRocketsUseCaseTest {

    @RelaxedMockK
    private lateinit var rocketRepository: RocketRepository

    private lateinit var objectUnderTest: RefreshRocketsUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        setUpRefreshRocketsUseCase()
    }

    @Test
    fun `should wrap result with success if repository doesn't throw`() = runTest {
        // Given
        coEvery { rocketRepository.refreshRockets() } just Runs

        // When
        val result = objectUnderTest.invoke()

        // Then
        assertEquals(
            expected = Result.success(Unit),
            actual = result,
        )
    }

    @Test
    fun `should rethrow if repository throws CancellationException`() = runTest {
        // Given
        coEvery { rocketRepository.refreshRockets() } throws CancellationException()

        // When-Then
        assertThrows<CancellationException> {
            objectUnderTest.invoke()
        }
    }

    @Test
    fun `should wrap result with failure if repository throws other Throwable`() = runTest {
        // Given
        val testException = Throwable("Test message")
        coEvery { rocketRepository.refreshRockets() } throws testException

        // When-Then
        assertThrows<Throwable> {
            val result = objectUnderTest.invoke()

            assertEquals(
                expected = Result.failure(testException),
                actual = result,
            )
        }
    }

    private fun setUpRefreshRocketsUseCase() {
        objectUnderTest = RefreshRocketsUseCase {
            refreshRockets(rocketRepository)
        }
    }
}
