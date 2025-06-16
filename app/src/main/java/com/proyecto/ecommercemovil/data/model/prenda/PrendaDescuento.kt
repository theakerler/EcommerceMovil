package com.proyecto.ecommercemovil.data.model.prenda

data class PrendaDescuento(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenPrincipal: String,
    val marca: String,
    val descuentoAplicado: Double,
    val tipoDescuento: String
)