package com.example.deepart.features.main.common

data class MainState(
    val mainUiState: MainUiState
)

sealed class MainUiState {
    object Init : MainUiState()
}
