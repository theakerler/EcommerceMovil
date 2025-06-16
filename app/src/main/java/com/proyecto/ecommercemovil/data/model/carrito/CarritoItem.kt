package com.proyecto.ecommercemovil.data.model.carrito

import com.proyecto.ecommercemovil.data.model.prenda.PrendaDetalle
import com.proyecto.ecommercemovil.data.model.prenda.detalles.Talla

data class CarritoItem(
    val id: Int,
    val prenda: PrendaDetalle,
    val talla: Talla,
    val cantidad: Int,
    val precioUnitario: Double
)