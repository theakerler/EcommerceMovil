package com.proyecto.ecommercemovil.ui.screens.sesion

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.repository.AuthRepository
import com.proyecto.ecommercemovil.data.model.TokenResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _token = MutableStateFlow<TokenResponse?>(null)
    val token: StateFlow<TokenResponse?> = _token

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun login(username: String, password: String) {
        Log.d("LoginViewModel", "login llamado con $username")
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                Log.d("LoginViewModel", "Login exitoso: ${response.accessToken}")
                _token.value = response
                _error.value = null
                repository.saveToken(response.accessToken) // Guarda el token aquí
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error en login", e)
                _token.value = null
                _error.value = "Login fallido: ${e.message}"
            }
        }
    }
    fun loginWithGoogle(idToken: String, clientId: String) {
        viewModelScope.launch {
            try {
                val response = repository.loginWithGoogle(idToken, clientId)
                Log.d("GoogleLogin", "AccessToken: ${response.accessToken}")
                Log.d("GoogleLogin", "RefreshToken: ${response.refreshToken}")
                _token.value = response
                _error.value = null
                repository.saveToken(response.accessToken) // Guarda el token aquí también
            } catch (e: Exception) {
                Log.e("GoogleLogin", "Error autenticando con Google", e)
                _token.value = null
                _error.value = "Error autenticando con Google: ${e.message}"
            }
        }
    }
}