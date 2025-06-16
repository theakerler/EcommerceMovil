package com.proyecto.ecommercemovil.data.model.carrito

data class CarritoRequestDto(
    val id: Long? = null,
    val usuarioId: Long,
    val estado: String = "ABIERTO"
)