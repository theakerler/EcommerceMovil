package com.proyecto.ecommercemovil.ui.viewmodel.prenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.ecommercemovil.data.network.PrendaApiService
import com.proyecto.ecommercemovil.data.repository.AuthRepository
import com.proyecto.ecommercemovil.data.repository.CarritoRepository

class PrendaDetalleViewModelFactory(
    private val apiService: PrendaApiService,
    private val authRepository: AuthRepository,
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrendaDetalleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrendaDetalleViewModel(apiService, authRepository, carritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}