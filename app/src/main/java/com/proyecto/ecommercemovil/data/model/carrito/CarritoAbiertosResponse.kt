package com.proyecto.ecommercemovil.data.model.carrito

data class CarritoAbiertosResponse(
    val mensaje: String,
    val `object`: List<Carrito>? // Puede ser null si no hay registros
)
