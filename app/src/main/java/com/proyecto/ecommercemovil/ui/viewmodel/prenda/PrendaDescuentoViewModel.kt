package com.proyecto.ecommercemovil.ui.viewmodel.prenda
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.model.prenda.PrendaDescuento
import com.proyecto.ecommercemovil.data.network.PrendaApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrendaDescuentoViewModel(private val api: PrendaApiService) : ViewModel() {
    private val _prendas = MutableStateFlow<List<PrendaDescuento>>(emptyList())
    val prendas: StateFlow<List<PrendaDescuento>> = _prendas

    fun cargarPrendas(categoria: String) {
        viewModelScope.launch {
            try {
                val response = api.getPrendasConDescuento(categoria)
                _prendas.value = response.`object`
            } catch (e: Exception) {
                _prendas.value = emptyList()
            }
        }
    }
}