package com.proyecto.ecommercemovil.data.model.carrito

data class CarritoItemRequestDto(
    val carritoId: Int,
    val prendaId: Int,
    val talla: String,
    val cantidad: Int,
    val precioUnitario: Double
)