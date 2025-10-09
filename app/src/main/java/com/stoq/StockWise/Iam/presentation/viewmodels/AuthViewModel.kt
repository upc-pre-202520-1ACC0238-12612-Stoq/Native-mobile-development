package com.stoq.StockWise.Iam.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoq.StockWise.Iam.data.repository.AuthRepository
import com.stoq.StockWise.Iam.domain.models.User
import com.stoq.StockWise.Iam.domain.validation.UserValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

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
                val success = authRepository.login(user)
                _loginSuccess.value = success
                if (!success) {
                    _errorMessage.value = "Invalid credentials. Please check your email and password."
                }
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
                val success = authRepository.register(user)
                _registerSuccess.value = success
                if (!success) {
                    _errorMessage.value = "Registration failed. Please try again."
                }
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
            authRepository.logout()
            clearUser()
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
