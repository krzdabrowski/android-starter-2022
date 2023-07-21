package eu.krzdabrowski.starter.basicfeature.presentation

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RocketsUiState(
    val isLoading: Boolean = false,
    val rockets: List<RocketDisplayable> = emptyList(),
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        data object Loading : PartialState() // for simplicity: initial loading & refreshing

        data class Fetched(val list: List<RocketDisplayable>) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}
