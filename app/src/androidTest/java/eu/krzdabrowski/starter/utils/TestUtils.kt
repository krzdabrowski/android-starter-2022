package eu.krzdabrowski.starter.utils

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.lifecycle.ViewModel

inline fun <reified T : ViewModel> AndroidComposeTestRule<*, *>.getHiltTestViewModel() =
    activity
        .viewModels<T>()
        .value
