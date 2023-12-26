package com.example.deepart.features.main.common

import android.graphics.Bitmap

data class MainState(
    val mainUiState: MainUiState
)

sealed class MainUiState {
    object Init : MainUiState()
    data class Result(val bitmap: Bitmap) : MainUiState()
}
