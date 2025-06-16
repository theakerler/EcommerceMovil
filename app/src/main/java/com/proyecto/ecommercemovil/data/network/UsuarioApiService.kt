package com.proyecto.ecommercemovil.data.network

import com.proyecto.ecommercemovil.data.model.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiService {
    @POST("api/v1/usuario")
    suspend fun registrarUsuario(@Body usuario: UsuarioDto): Response<UsuarioDto>
}