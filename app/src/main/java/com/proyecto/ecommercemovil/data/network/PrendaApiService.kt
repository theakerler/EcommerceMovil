package com.proyecto.ecommercemovil.data.network

import com.proyecto.ecommercemovil.data.model.prenda.PrendaDescuentoResponse
import com.proyecto.ecommercemovil.data.model.prenda.PrendaDetalleResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PrendaApiService {
    @GET("api/v1/prendas/descuentos-aplicados/{categoria}")
    suspend fun getPrendasConDescuento(@Path("categoria") categoria: String): PrendaDescuentoResponse

    @GET("api/v1/prenda/{id}")
    suspend fun getPrendaDetalle(@Path("id") id: Int): PrendaDetalleResponse

}