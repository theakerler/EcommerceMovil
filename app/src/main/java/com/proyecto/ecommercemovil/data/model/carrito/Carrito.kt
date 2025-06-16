package com.proyecto.ecommercemovil.data.model.carrito

import com.proyecto.ecommercemovil.data.model.prenda.PrendaDetalle
import com.proyecto.ecommercemovil.data.model.prenda.detalles.Talla

data class Carrito(
    val id: Int,
    val usuarioId: Int,
    val fechaCreacion: String,
    val estado: String,
    val carritoItems: List<CarritoItem>
)