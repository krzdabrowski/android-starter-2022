package eu.krzdabrowski.starter.core.presentation.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class EventDelegateImpl<EVENT> : EventDelegate<EVENT> {

    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)

    override fun getEvents(): Flow<EVENT> = eventChannel.receiveAsFlow()

    override suspend fun setEvent(event: EVENT) {
        eventChannel.send(event)
    }
}
