package com.proyecto.ecommercemovil.data.network

import com.proyecto.ecommercemovil.data.model.carrito.CantidadResponse
import com.proyecto.ecommercemovil.data.model.carrito.CarritoItemRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoRequestDto
import com.proyecto.ecommercemovil.data.model.carrito.CarritoAbiertosResponse
import com.proyecto.ecommercemovil.data.model.carrito.CarritoResponse
import com.proyecto.ecommercemovil.data.model.carrito.CarritoSingleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface CarritoApiService {
    @GET("api/v1/carrito/abierto/usuario/{usuarioId}")
    suspend fun getCarritoAbierto(
        @Path("usuarioId") usuarioId: Int,
        @Header("Authorization") authHeader: String
    ): Response<CarritoAbiertosResponse>

    @POST("api/v1/carrito")
    suspend fun crearCarrito(
        @Body request: CarritoRequestDto,
        @Header("Authorization") authHeader: String
    ): Response<CarritoSingleResponse>
    // En CarritoApiService.kt

    @POST("api/v1/carrito-item/agregar")
    suspend fun agregarCarritoItem(
        @Query("carritoId") carritoId: Int,
        @Query("prendaId") prendaId: Int,
        @Query("tallaId") tallaId: Int,
        @Header("Authorization") authHeader: String
    ): Response<Unit> // Cambia el tipo si tu API retorna algo específico

    @POST("api/v1/carrito-item")
    suspend fun agregarCarritoItemBody(
        @Body request: CarritoItemRequestDto,
        @Header("Authorization") authHeader: String
    ): Response<Unit> // Cambia Unit si tu API retorna algo específico


    @GET("api/v1/carrito/{carritoId}/cantidad-items")
    suspend fun getCantidadItemsCarrito(
        @Path("carritoId") carritoId: Int,
        @Header("Authorization") authHeader: String
    ): Response<CantidadResponse>

    @GET("api/v1/carrito/{id}")
    suspend fun getCarrito(@Path("id") id: Int): CarritoResponse

    @PUT("carrito-item/{itemId}/cantidad")
    suspend fun actualizarCantidadItem(
        @Path("itemId") itemId: Int,
        @Query("cantidad") cantidad: Int
    )

}