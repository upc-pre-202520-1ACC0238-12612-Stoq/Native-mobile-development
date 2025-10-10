package com.stoq.StockWise.Iam.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.domain.models.User
import com.stoq.StockWise.Iam.domain.validation.UserValidator
import com.stoq.StockWise.shared.domain.events.EventBus
import com.stoq.StockWise.shared.domain.events.LoginSuccessEvent
import com.stoq.StockWise.shared.domain.events.RegisterSuccessEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _loginSuccess = MutableStateFlow<Boolean?>(null)
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess
    
    private val _registerSuccess = MutableStateFlow<Boolean?>(null)
    val registerSuccess: StateFlow<Boolean?> = _registerSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateEmail(email: String) {
        _user.value = _user.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _user.value = _user.value.copy(password = password)
    }

    fun updateUsername(username: String) {
        _user.value = _user.value.copy(username = username)
    }

    fun clearUser() {
        _user.value = User()
    }

    fun login() {
        val user = _user.value
        val validationResult = UserValidator.validateForLogin(user)

        if (!validationResult.isValid) {
            _errorMessage.value = validationResult.errors.firstOrNull()
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.login(_user.value)
                result.fold(
                    onSuccess = { success ->
                        _loginSuccess.value = success
                        if (success) {
                            // Emitir evento de login exitoso
                            EventBus.emit(LoginSuccessEvent(
                                userId = _user.value.id ?: 1, // Por ahora usar ID 1
                                token = "mock_token_${_user.value.id ?: 1}"
                            ))
                            _uiState.value = _uiState.value.copy(isAuthenticated = true)
                        } else {
                            _errorMessage.value = "Email o contraseña incorrectos."
                        }
                    },
                    onFailure = { error ->
                        _errorMessage.value = "Error de autenticación: ${error.message}"
                        _loginSuccess.value = false
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Authentication error: ${e.message}"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register() {
        val user = _user.value
        val validationResult = UserValidator.validateForRegistration(user)

        if (!validationResult.isValid) {
            _errorMessage.value = validationResult.errors.firstOrNull()
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.register(_user.value)
                result.fold(
                    onSuccess = { success ->
                        _registerSuccess.value = success
                        if (success) {
                            // Emitir evento de registro exitoso
                            EventBus.emit(RegisterSuccessEvent(
                                userId = _user.value.id ?: 1, // Por ahora usar ID 1
                                token = "mock_token_${_user.value.id ?: 1}"
                            ))
                            _uiState.value = _uiState.value.copy(isAuthenticated = true)
                        } else {
                            _errorMessage.value = "Error en el registro. Intente nuevamente."
                        }
                    },
                    onFailure = { error ->
                        _errorMessage.value = "Error de registro: ${error.message}"
                        _registerSuccess.value = false
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Registration error: ${e.message}"
                _registerSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout().fold(
                    onSuccess = { 
                        clearUser()
                        _uiState.value = _uiState.value.copy(isAuthenticated = false)
                    },
                    onFailure = { error ->
                        _errorMessage.value = "Error al cerrar sesión: ${error.message}"
                    }
                )
            } catch (e: Exception) {
                _errorMessage.value = "Error al cerrar sesión: ${e.message}"
            }
        }
    }

    fun resetLoginSuccess() {
        _loginSuccess.value = null
    }
    
    fun resetRegisterSuccess() {
        _registerSuccess.value = null
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}

/**
 * Estado de la UI para autenticación
 */
data class AuthUiState(
    val isAuthenticated: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
