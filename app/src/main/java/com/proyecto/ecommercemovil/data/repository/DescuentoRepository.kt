// app/src/main/java/com/proyecto/ecommercemovil/data/repository/DescuentoRepository.kt
package com.proyecto.ecommercemovil.data.repository

import com.proyecto.ecommercemovil.data.model.descuento.DescuentoRequest
import com.proyecto.ecommercemovil.data.model.descuento.DescuentoResponse
import com.proyecto.ecommercemovil.data.network.DescuentoApiService

class DescuentoRepository(private val api: DescuentoApiService) {
    suspend fun aplicarDescuento(usuarioId: Int, codigo: String): DescuentoResponse? {
        val response = api.aplicarDescuento(DescuentoRequest(usuarioId, codigo))
        return if (response.isSuccessful) response.body() else null
    }
}