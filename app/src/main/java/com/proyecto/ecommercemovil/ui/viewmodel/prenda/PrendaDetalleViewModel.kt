package com.proyecto.ecommercemovil.ui.viewmodel.prenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.proyecto.ecommercemovil.data.model.carrito.CarritoItemRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoRequestDto
import com.proyecto.ecommercemovil.data.model.prenda.PrendaDetalle
import com.proyecto.ecommercemovil.data.network.PrendaApiService
import com.proyecto.ecommercemovil.data.repository.AuthRepository
import com.proyecto.ecommercemovil.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrendaDetalleViewModel(
    private val api: PrendaApiService,
    private val authRepository: AuthRepository,
    private val carritoRepository: CarritoRepository
) : ViewModel() {

    private val _prenda = MutableStateFlow<PrendaDetalle?>(null)
    val prenda: StateFlow<PrendaDetalle?> = _prenda

    fun cargarPrenda(id: Int) {
        viewModelScope.launch {
            try {
                val response = api.getPrendaDetalle(id)
                _prenda.value = response.`object`
            } catch (e: Exception) {
                _prenda.value = null
            }
        }
    }

    fun agregarAlCarrito(
        prendaId: Int,
        tallaId: Int,
        precioConDescuento: Double,
        onSuccess: (String, Int) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = authRepository.getToken()
                if (token == null) {
                    onError("Necesitas iniciar sesión")
                    return@launch
                }
                val usuarioId = authRepository.getUsuarioId(token)
                val carritoResponse = carritoRepository.getCarritoAbierto(usuarioId, token)
                val carritoList = carritoResponse.body()?.`object`
                val carritoId = if (carritoResponse.isSuccessful && !carritoList.isNullOrEmpty()) {
                    carritoList[0].id
                } else {
                    val nuevoCarrito = CarritoRequestDto(usuarioId = usuarioId.toLong())
                    val crearResponse = carritoRepository.crearCarrito(nuevoCarrito, token)
                    crearResponse.body()?.`object`?.id ?: throw Exception("No se pudo crear el carrito")
                }

                // Guardar el carritoId en preferencias
                carritoRepository.saveCarritoId(carritoId)

                val incrementarResponse = carritoRepository.agregarCarritoItem(carritoId, prendaId, tallaId, token)
                if (incrementarResponse.isSuccessful) {
                    onSuccess("Producto agregado al carrito", carritoId)
                } else {
                    val tallaNombre = _prenda.value?.tallas?.find { it.talla.id == tallaId }?.talla?.nomTalla ?: ""
                    val itemRequest = CarritoItemRequestDto(
                        carritoId = carritoId,
                        prendaId = prendaId,
                        talla = tallaNombre,
                        cantidad = 1,
                        precioUnitario = precioConDescuento
                    )
                    val agregarResponse = carritoRepository.agregarCarritoItemBody(itemRequest, token)
                    if (agregarResponse.isSuccessful) {
                        onSuccess("Producto agregado al carrito", carritoId)
                    } else {
                        onError("No se pudo agregar el producto al carrito")
                        return@launch
                    }
                }
                carritoRepository.getCantidadItemsCarrito(carritoId, token)
            } catch (e: Exception) {
                onError("No se pudo agregar el producto al carrito")
            }
        }
    }
}