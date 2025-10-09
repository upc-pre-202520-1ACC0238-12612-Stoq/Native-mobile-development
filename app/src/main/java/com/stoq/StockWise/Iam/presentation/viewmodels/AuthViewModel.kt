package com.stoq.StockWise.presentation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.domain.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _loginSuccess = MutableStateFlow<Boolean?>(null) // ← Cambiado a Boolean
    val loginSuccess: StateFlow<Boolean?> = _loginSuccess

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
        val email = _user.value.email
        val password = _user.value.password

        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "El email no es válido."
            return
        }
        if (password.isBlank() || password.length < 4) {
            _errorMessage.value = "La contraseña debe tener al menos 4 caracteres."
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val success = authRepository.login(_user.value) // ← Esto devuelve Boolean
                _loginSuccess.value = success
                if (!success) {
                    _errorMessage.value = "Email o contraseña incorrectos."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de autenticación: ${e.message}"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register() {
        val email = _user.value.email
        val password = _user.value.password
        val username = _user.value.username

        if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "El email no es válido."
            return
        }
        if (password.isBlank() || password.length < 3) {
            _errorMessage.value = "La contraseña debe tener al menos 3 caracteres."
            return
        }
        if (username.isBlank()) {
            _errorMessage.value = "El nombre de usuario es requerido."
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val success = authRepository.register(_user.value) // ← Esto devuelve Boolean
                _loginSuccess.value = success
                if (!success) {
                    _errorMessage.value = "Error en el registro. Intente nuevamente."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de registro: ${e.message}"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            clearUser()
        }
    }

    fun resetLoginSuccess() {
        _loginSuccess.value = null
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}
