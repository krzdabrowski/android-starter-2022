package eu.krzdabrowski.starter.core.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface EventDelegate<EVENT> {
    fun getEvents(): Flow<EVENT>
    suspend fun setEvent(event: EVENT)
}
