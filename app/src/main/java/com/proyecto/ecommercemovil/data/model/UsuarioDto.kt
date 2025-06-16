package com.proyecto.ecommercemovil.data.model

data class UsuarioDto(
    val id: Long? = null,
    val nombreUsuario: String,
    val email: String,
    val contrasenia: String,
    val rol: String = "USER" // o el rol que corresponda
)