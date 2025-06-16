package com.proyecto.ecommercemovil.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.network.AuthApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    application: Application,
    private val api: AuthApiService
) : AndroidViewModel(application) {

    private val _usuarioId = MutableStateFlow<Int?>(null)
    val usuarioId: StateFlow<Int?> = _usuarioId

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchUsuarioId() {
        viewModelScope.launch {
            val prefs = getApplication<Application>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val token = prefs.getString("accessToken", null)
            if (token == null) {
                _error.value = "No hay token de acceso"
                return@launch
            }
            try {
                val response = api.getUsuarioId("Bearer $token")
                if (response.isSuccessful) {
                    val id = response.body()?.toIntOrNull()
                    _usuarioId.value = id
                    _error.value = null
                } else {
                    _error.value = "Error al obtener usuarioId"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }
}