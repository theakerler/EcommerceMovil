package com.proyecto.ecommercemovil.data.model.direccion



// Modelos auxiliares
data class DepartamentoConProvincias(
    val departamento_id: String,
    val departamento_nombre: String,
    val provincias: List<ProvinciaSimple>
)
