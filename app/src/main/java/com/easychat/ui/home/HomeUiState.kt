package com.easychat.ui.home

data class HomeUiState(
    val chatCount: Int = 0,
    val videoCallCount: Int = 0,
    val lastAction: String = "Aún no hay interacciones",
    val statusMessage: String = "Explora las acciones flotantes de EasyChat"
)
