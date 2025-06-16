package com.proyecto.ecommercemovil.ui.viewmodel.carrito

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.ecommercemovil.data.network.CarritoApiService
import com.proyecto.ecommercemovil.data.repository.CarritoRepository

class CarritoViewModelFactory(
    private val application: Application,
    private val api: CarritoApiService,
    private val carritoRepository: CarritoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarritoViewModel(application, api, carritoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}