package com.easychat.ui.auth

data class AuthUiState(
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isRegisterMode: Boolean = false,
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)
