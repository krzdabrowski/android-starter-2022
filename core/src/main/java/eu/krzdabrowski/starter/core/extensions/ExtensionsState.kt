package eu.krzdabrowski.starter.core.extensions

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import eu.krzdabrowski.starter.core.base.BaseViewModel

@Composable
fun <UI_STATE : Parcelable, UI_EVENT, PARTIAL_UI_STATE, INTENT> getLifecycleAwareUiState(
    viewModel: BaseViewModel<UI_STATE, UI_EVENT, PARTIAL_UI_STATE, INTENT>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<UI_STATE> {
    return remember(viewModel.uiState, lifecycleOwner) {
        viewModel.uiState.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            minActiveState
        )
    }.collectAsState(viewModel.initialState)
}
