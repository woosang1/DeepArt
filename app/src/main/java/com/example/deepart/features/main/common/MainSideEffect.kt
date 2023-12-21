package com.example.deepart.features.main.common

sealed class MainSideEffect {
    object OpenCamera : MainSideEffect()
    object OpenGallery : MainSideEffect()
    object EditImage : MainSideEffect()
    object SaveImage : MainSideEffect()
    object StartCommunity : MainSideEffect()
    object ShowOptionPopup : MainSideEffect()
    data class ShowToast(val message: String) : MainSideEffect()
}