package eu.krzdabrowski.starter.core.presentation.mvi

import kotlinx.coroutines.flow.Flow

interface IntentDelegate<INTENT, PARTIAL_UI_STATE> {
    fun getIntents(
        mapOperation: (INTENT) -> Flow<PARTIAL_UI_STATE>,
    ): Flow<PARTIAL_UI_STATE>

    suspend fun setIntent(intent: INTENT)
}
