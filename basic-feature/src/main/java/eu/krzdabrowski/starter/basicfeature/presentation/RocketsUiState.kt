package eu.krzdabrowski.starter.basicfeature.presentation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RocketsUiState(
    val dummy: String = ""
) : Parcelable {

    sealed class PartialState {
        object Dummy : PartialState()
    }
}
