// app/src/main/java/com/proyecto/ecommercemovil/ui/viewmodel/descuento/DescuentoViewModel.kt
package com.proyecto.ecommercemovil.ui.viewmodel.descuento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.model.descuento.Descuento
import com.proyecto.ecommercemovil.data.repository.DescuentoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DescuentoViewModel(private val repository: DescuentoRepository) : ViewModel() {
    private val _descuento = MutableStateFlow<Descuento?>(null)
    val descuento: StateFlow<Descuento?> = _descuento

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje: StateFlow<String?> = _mensaje

    fun aplicarDescuento(usuarioId: Int, codigo: String) {
        viewModelScope.launch {
            val response = repository.aplicarDescuento(usuarioId, codigo)
            _mensaje.value = response?.mensaje
            _descuento.value = response?.`object`
        }
    }
}