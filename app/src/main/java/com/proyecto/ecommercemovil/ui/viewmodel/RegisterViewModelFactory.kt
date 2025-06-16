// app/src/main/java/com/proyecto/ecommercemovil/ui/viewmodel/RegisterViewModelFactory.kt
package com.proyecto.ecommercemovil.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.ecommercemovil.data.network.UsuarioApiService

class RegisterViewModelFactory(
    private val usuarioApiService: UsuarioApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(usuarioApiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}