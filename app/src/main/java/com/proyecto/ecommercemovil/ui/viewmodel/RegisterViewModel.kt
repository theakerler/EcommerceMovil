// app/src/main/java/com/proyecto/ecommercemovil/ui/viewmodel/RegisterViewModel.kt
package com.proyecto.ecommercemovil.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.model.UsuarioDto
import com.proyecto.ecommercemovil.data.network.UsuarioApiService
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(
    private val usuarioApiService: UsuarioApiService
) : ViewModel() {

    var registroExitoso by mutableStateOf(false)
        private set

    var error: String? by mutableStateOf(null)
        private set


    fun registrarUsuario(nombreUsuario: String, email: String, contrasenia: String) {
        viewModelScope.launch {
            try {
                val usuario = UsuarioDto(
                    nombreUsuario = nombreUsuario,
                    email = email,
                    contrasenia = contrasenia
                )
                val response: Response<UsuarioDto> = usuarioApiService.registrarUsuario(usuario)
                registroExitoso = response.isSuccessful
                Log.d("RegisterViewModel", "Registro exitoso: $registroExitoso")
                if (!registroExitoso) {
                    error = "Error en el registro"
                    Log.d("RegisterViewModel", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                error = e.message
                Log.d("RegisterViewModel", "Excepción: ${e.message}")
            }
        }
    }
}