package com.example.deepart.features.main.common

import android.graphics.Bitmap

sealed class MainSideEffect {
    object OpenCamera : MainSideEffect()
    object OpenGallery : MainSideEffect()
    object EditImage : MainSideEffect()
    object SaveImage : MainSideEffect()
    object StartCommunity : MainSideEffect()
    object ShowOptionPopup : MainSideEffect()
    data class TransImage(val style: Bitmap) : MainSideEffect()

    data class ShowToast(val message: String) : MainSideEffect()
}