package com.example.deepart.features.main.common

import android.content.Context
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.example.deepart.R
import dagger.hilt.android.qualifiers.ApplicationContext

data class MainState(
    val mainUiState: MainUiState
)

sealed class MainUiState {

    abstract val bitmap: Bitmap?
    data class Init(
        override val bitmap: Bitmap? = null
    ) : MainUiState()
    data class Result(override val bitmap: Bitmap?) : MainUiState()
}
