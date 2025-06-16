package com.proyecto.ecommercemovil.data.model.carrito

data class CarritoResponse(
    val mensaje: String,
    val `object`: Carrito? // Puede ser null si no hay registros
)
