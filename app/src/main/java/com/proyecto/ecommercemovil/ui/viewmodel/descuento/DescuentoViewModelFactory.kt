package com.proyecto.ecommercemovil.ui.viewmodel.descuento

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.proyecto.ecommercemovil.data.repository.DescuentoRepository

class DescuentoViewModelFactory(
    private val application: Application,
    private val repository: DescuentoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescuentoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DescuentoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}