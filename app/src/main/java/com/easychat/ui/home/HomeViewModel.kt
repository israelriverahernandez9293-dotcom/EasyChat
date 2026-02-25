package com.easychat.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.easychat.data.local.LocalInteractionStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val localStore = LocalInteractionStore(application)

    private val _uiState = MutableStateFlow(
        HomeUiState(
            chatCount = localStore.getChatCount(),
            videoCallCount = localStore.getVideoCallCount(),
            lastAction = localStore.getLastAction()
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun startChat() {
        localStore.registerChatAction()
        _uiState.update {
            it.copy(
                chatCount = localStore.getChatCount(),
                lastAction = localStore.getLastAction(),
                statusMessage = "Modo chat activado (simulación local)."
            )
        }
    }

    fun startVideoCall() {
        localStore.registerVideoCallAction()
        _uiState.update {
            it.copy(
                videoCallCount = localStore.getVideoCallCount(),
                lastAction = localStore.getLastAction(),
                statusMessage = "Videollamada iniciada (simulación local)."
            )
        }
    }

    companion object {
        fun factory(application: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return HomeViewModel(application) as T
                }
            }
    }
}
