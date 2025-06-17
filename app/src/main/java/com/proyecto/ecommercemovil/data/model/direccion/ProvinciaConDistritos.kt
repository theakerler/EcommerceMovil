package com.proyecto.ecommercemovil.data.model.direccion

// Modelos auxiliares para parsear el JSON
data class ProvinciaConDistritos(
    val provincia_id: String,
    val provincia_nombre: String,
    val departamento_id: String,
    val distritos: List<DistritoSimple>
)