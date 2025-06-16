package com.proyecto.ecommercemovil.data.repository

import android.content.Context
import com.proyecto.ecommercemovil.data.model.carrito.CarritoItemRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoAbiertosResponse
import com.proyecto.ecommercemovil.data.model.carrito.CarritoResponse
import com.proyecto.ecommercemovil.data.network.CarritoApiService

class CarritoRepository(
    private val api: CarritoApiService,
    private val context: Context
) {

    suspend fun getCarritoAbierto(usuarioId: Int, token: String) =
        api.getCarritoAbierto(usuarioId, "Bearer $token")

    suspend fun crearCarrito(request: CarritoRequestDto, token: String) =
        api.crearCarrito(request, "Bearer $token")

    suspend fun agregarCarritoItem(carritoId: Int, prendaId: Int, tallaId: Int, token: String) =
        api.agregarCarritoItem(carritoId, prendaId, tallaId, "Bearer $token")

    suspend fun agregarCarritoItemBody(request: CarritoItemRequestDto, token: String) =
        api.agregarCarritoItemBody(request, "Bearer $token")

    suspend fun getCantidadItemsCarrito(carritoId: Int, token: String) =
        api.getCantidadItemsCarrito(carritoId, "Bearer $token")

    suspend fun obtenerCarrito(id: Int): CarritoResponse {
        return api.getCarrito(id)
    }

    fun saveCarritoId(carritoId: Int) {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("carritoId", carritoId).apply()
    }

    fun getCarritoId(): Int? {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val id = prefs.getInt("carritoId", -1)
        return if (id != -1) id else null
    }

    suspend fun actualizarCantidadItem(itemId: Int, cantidad: Int) {
        api.actualizarCantidadItem(itemId, cantidad)
    }

    suspend fun eliminarCarritoItem(itemId: Int): Boolean {
        val response = api.eliminarCarritoItem(itemId)
        return response.isSuccessful
    }
}