package com.easychat.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.easychat.data.AuthRepository
import com.easychat.domain.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            isAuthenticated = repository.isCurrentUserVerified()
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) = updateState { copy(email = email) }
    fun onPhoneChange(phone: String) = updateState { copy(phone = phone) }
    fun onPasswordChange(password: String) = updateState { copy(password = password) }
    fun onConfirmPasswordChange(confirmPassword: String) = updateState { copy(confirmPassword = confirmPassword) }

    fun toggleMode() {
        updateState {
            copy(
                isRegisterMode = !isRegisterMode,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun logout() {
        repository.signOut()
        updateState { copy(isAuthenticated = false, successMessage = "Sesión cerrada") }
    }

    fun submit() {
        val current = uiState.value
        if (current.email.isBlank() || current.password.isBlank()) {
            updateState { copy(errorMessage = "Email y contraseña son obligatorios", successMessage = null) }
            return
        }

        if (current.isRegisterMode) {
            if (current.phone.isBlank()) {
                updateState { copy(errorMessage = "El teléfono es obligatorio para registrarse", successMessage = null) }
                return
            }
            if (current.password != current.confirmPassword) {
                updateState { copy(errorMessage = "Las contraseñas no coinciden", successMessage = null) }
                return
            }
        }

        viewModelScope.launch {
            updateState { copy(isLoading = true, errorMessage = null, successMessage = null) }
            val result = if (current.isRegisterMode) {
                repository.register(current.email.trim(), current.phone.trim(), current.password)
            } else {
                repository.login(current.email.trim(), current.password)
            }

            when (result) {
                is AuthResult.Success -> {
                    updateState {
                        copy(
                            isLoading = false,
                            isAuthenticated = !current.isRegisterMode,
                            successMessage = if (current.isRegisterMode) {
                                "Cuenta creada. Revisa tu correo para confirmar tu cuenta."
                            } else {
                                "Inicio de sesión exitoso."
                            },
                            errorMessage = null
                        )
                    }
                }

                is AuthResult.Error -> {
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = result.message,
                            successMessage = null
                        )
                    }
                }
            }
        }
    }

    private fun updateState(transform: AuthUiState.() -> AuthUiState) {
        _uiState.update { it.transform() }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(AuthRepository()) as T
            }
        }
    }
}
