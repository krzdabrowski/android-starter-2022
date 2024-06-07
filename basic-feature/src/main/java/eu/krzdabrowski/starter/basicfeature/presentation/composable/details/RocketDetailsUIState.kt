package eu.krzdabrowski.starter.basicfeature.presentation.composable.details

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import eu.krzdabrowski.starter.basicfeature.presentation.model.RocketDisplayable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class RocketDetailsUIState(
    val rocket: RocketDisplayable? = null,
    val showError: Boolean = false
) : Parcelable {

    sealed class PartialState {
        data class Fetched(val rocket: RocketDisplayable?) : PartialState()
        data object Failed : PartialState()
    }
}