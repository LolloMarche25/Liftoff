package com.example.liftoff.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liftoff.data.repository.AuthRepository
import com.example.liftoff.data.repository.SettingsRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var username by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var isAuthenticated by mutableStateOf(authRepository.currentUser != null)
        private set

    fun onEmailChange(value: String) { email = value }
    fun onPasswordChange(value: String) { password = value }
    fun onUsernameChange(value: String) { username = value }

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Please fill in all fields."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                isAuthenticated = true
            } else {
                val exception = result.exceptionOrNull()
                errorMessage = when {
                    exception?.message?.contains("INVALID_CREDENTIAL") == true ->
                        "Wrong email or password."
                    exception?.message?.contains("USER_NOT_FOUND") == true ->
                        "No account found with this email."
                    else -> "Login failed. Check your credentials."
                }
            }
            isLoading = false
        }
    }

    fun register() {
        if (username.isBlank()) {
            errorMessage = "Please enter a username."
            return
        }
        if (!email.contains("@")) {
            errorMessage = "Please enter a valid email."
            return
        }
        if (password.length < 6) {
            errorMessage = "Password must be at least 6 characters."
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = authRepository.register(email, password)
            if (result.isSuccess) {
                settingsRepository.setUsername(username)
                settingsRepository.setEmail(email)
                isAuthenticated = true
            } else {
                val exception = result.exceptionOrNull()
                errorMessage = when {
                    exception?.message?.contains("EMAIL_ALREADY_IN_USE") == true ->
                        "Email already registered."
                    exception?.message?.contains("WEAK_PASSWORD") == true ->
                        "Password too weak. Use at least 6 characters."
                    else -> "Registration failed. Try again."
                }
            }
            isLoading = false
        }
    }

    fun logout() {
        authRepository.logout()
        isAuthenticated = false
    }
}