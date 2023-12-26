package com.example.deepart.features.main

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.deepart.DeepArtApplication
import com.example.deepart.core.base.BaseViewModel
import com.example.deepart.features.main.common.MainSideEffect
import com.example.deepart.features.main.common.MainState
import com.example.deepart.features.main.common.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(MainState(MainUiState.Init()))
    var currentImagePath: String = ""

    fun changeResultState(bitmap: Bitmap) = intent {
        viewModelScope.launch {
            reduce {
                state.copy(MainUiState.Result(bitmap = bitmap))
            }
        }
    }

    fun postAction(sideEffect: MainSideEffect) = intent {
        viewModelScope.launch {
            postSideEffect(sideEffect = sideEffect)
        }
    }

}