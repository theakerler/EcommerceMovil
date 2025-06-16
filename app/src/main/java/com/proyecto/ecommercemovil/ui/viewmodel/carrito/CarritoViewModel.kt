package com.proyecto.ecommercemovil.ui.viewmodel.carrito

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.model.carrito.Carrito
import com.proyecto.ecommercemovil.data.model.carrito.CarritoItemRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoRequestDto
import com.proyecto.ecommercemovil.data.network.CarritoApiService
import com.proyecto.ecommercemovil.data.repository.CarritoRepository
import com.proyecto.ecommercemovil.di.AppModule.carritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    application: Application,
    private val api: CarritoApiService,
    private val carritoRepository: CarritoRepository


) : AndroidViewModel(application) {

    private val _carrito = MutableStateFlow<Carrito?>(null)
    val carrito: StateFlow<Carrito?> = _carrito

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun crearCarrito(usuarioId: Long, token: String) {
        viewModelScope.launch {
            try {
                val request = CarritoRequestDto(usuarioId = usuarioId, estado = "ABIERTO")
                val response = api.crearCarrito(request, "Bearer $token")
                if (response.isSuccessful) {
                    val carrito = response.body()?.`object`
                    val carritoId = carrito?.id
                    if (carritoId != null) {
                        val prefs = getApplication<Application>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        prefs.edit().putInt("carritoId", carritoId.toInt()).apply()
                        _carrito.value = carrito
                        _error.value = null
                    } else {
                        _error.value = "No se pudo obtener la id del carrito"
                    }
                } else {
                    _error.value = "Error al crear carrito"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun agregarItemAlCarrito(carritoId: Int, prendaId: Int, tallaId: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = api.agregarCarritoItem(carritoId, prendaId, tallaId, "Bearer $token")
                if (response.isSuccessful) {
                    // Item agregado correctamente
                } else {
                    _error.value = "Error al agregar item al carrito"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun agregarItemAlCarritoBody(
        carritoId: Int,
        prendaId: Int,
        talla: String,
        cantidad: Int,
        precioUnitario: Double,
        token: String
    ) {
        viewModelScope.launch {
            try {
                val request = CarritoItemRequestDto(carritoId, prendaId, talla, cantidad, precioUnitario)
                val response = api.agregarCarritoItemBody(request, "Bearer $token")
                if (response.isSuccessful) {
                    // Item agregado correctamente
                } else {
                    _error.value = "Error al agregar item al carrito"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun actualizarCantidadCarrito(carritoId: Int, token: String) {
        viewModelScope.launch {
            try {
                val response = api.getCantidadItemsCarrito(carritoId, "Bearer $token")
                if (response.isSuccessful) {
                    val cantidad = response.body()?.`object`
                    if (cantidad != null) {
                        val prefs = getApplication<Application>().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        prefs.edit().putInt("cantidadCarrito", cantidad).apply()
                    } else {
                        _error.value = "No se pudo obtener la cantidad"
                    }
                } else {
                    _error.value = "Error al obtener cantidad de items"
                }
            } catch (e: Exception) {
                _error.value = "Excepción: ${e.message}"
            }
        }
    }

    fun cargarCarrito(carritoId: Int) {
        viewModelScope.launch {
            try {
                val response = carritoRepository.obtenerCarrito(carritoId)
                _carrito.value = response.`object`
            } catch (e: Exception) {
                _error.value = "Error al cargar el carrito: ${e.message}"
                println("Excepción al cargar carrito: ${e}")
            }
        }
    }
   fun actualizarCantidad(itemId: Int, nuevaCantidad: Int) {
       viewModelScope.launch {
           try {
               println("Actualizando cantidad: itemId=$itemId, nuevaCantidad=$nuevaCantidad")
               if (nuevaCantidad < 1) return@launch
               carritoRepository.actualizarCantidadItem(itemId, nuevaCantidad)
               val carritoId = _carrito.value?.id
               println("CarritoId actual: $carritoId")
               if (carritoId != null) {
                   cargarCarrito(carritoId)
               } else {
                   _error.value = "Carrito no encontrado"
               }
           } catch (e: Exception) {
               _error.value = "Error al actualizar cantidad: ${e.message}"
               println("Error al actualizar cantidad: ${e.message}")
           }
       }
   }

    fun eliminarItem(itemId: Int) {
        viewModelScope.launch {
            try {
                val ok = carritoRepository.eliminarCarritoItem(itemId)
                if (ok) {
                    val carritoId = _carrito.value?.id
                    if (carritoId != null) cargarCarrito(carritoId)
                } else {
                    _error.value = "Error al eliminar item"
                }
            } catch (e: Exception) {
                _error.value = "Error al eliminar item: ${e.message}"
            }
        }
    }

}