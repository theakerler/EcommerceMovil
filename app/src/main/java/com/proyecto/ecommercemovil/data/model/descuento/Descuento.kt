package com.proyecto.ecommercemovil.data.model.descuento



data class Descuento(
    val id: Int,
    val codigo: String,
    val descripcion: String,
    val porcentaje: Double,
    val fechaInicio: String,
    val fechaFin: String,
    val usoMaximo: Int,
    val usado: Int,
    val activo: Boolean
)