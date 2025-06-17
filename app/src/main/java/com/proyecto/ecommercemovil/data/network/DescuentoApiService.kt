// app/src/main/java/com/proyecto/ecommercemovil/data/network/DescuentoApiService.kt
package com.proyecto.ecommercemovil.data.network

import com.proyecto.ecommercemovil.data.model.descuento.DescuentoRequest
import com.proyecto.ecommercemovil.data.model.descuento.DescuentoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DescuentoApiService {
    @POST("api/v1/aplicar")
    suspend fun aplicarDescuento(
        @Body request: DescuentoRequest
    ): Response<DescuentoResponse>
}