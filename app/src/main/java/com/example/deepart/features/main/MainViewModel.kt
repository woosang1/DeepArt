package com.example.deepart.features.main

import androidx.lifecycle.viewModelScope
import com.example.deepart.core.base.BaseViewModel
import com.example.deepart.features.main.common.MainUiState
import com.example.deepart.features.main.common.MainSideEffect
import com.example.deepart.features.main.common.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import javax.inject.Inject
import org.orbitmvi.orbit.viewmodel.container
import org.orbitmvi.orbit.syntax.simple.postSideEffect

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel(), ContainerHost<MainState, MainSideEffect> {

    override val container: Container<MainState, MainSideEffect> = container(MainState(MainUiState.Init))

    fun postAction(sideEffect: MainSideEffect) = intent {
        viewModelScope.launch {
            postSideEffect(sideEffect = sideEffect)
        }
    }

}