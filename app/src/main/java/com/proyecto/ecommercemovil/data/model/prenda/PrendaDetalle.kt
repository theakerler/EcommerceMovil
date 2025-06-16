package com.proyecto.ecommercemovil.data.model.prenda

import com.proyecto.ecommercemovil.data.model.prenda.detalles.Categoria
import com.proyecto.ecommercemovil.data.model.prenda.detalles.Imagen
import com.proyecto.ecommercemovil.data.model.prenda.detalles.Marca
import com.proyecto.ecommercemovil.data.model.prenda.detalles.Proveedor
import com.proyecto.ecommercemovil.data.model.prenda.detalles.TallaStock


data class PrendaDetalle(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagen: Imagen,
    val marca: Marca,
    val categoria: Categoria,
    val proveedor: Proveedor,
    val precio: Double,
    val activo: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val tallas: List<TallaStock>
)