package com.proyecto.ecommercemovil.ui.viewmodel.prenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.ecommercemovil.data.network.PrendaApiService

class PrendaDescuentoViewModelFactory(
    private val apiService: PrendaApiService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrendaDescuentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrendaDescuentoViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}