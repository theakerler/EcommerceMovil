package com.proyecto.ecommercemovil.data.network

import com.proyecto.ecommercemovil.data.model.GoogleLoginRequest
import com.proyecto.ecommercemovil.data.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @FormUrlEncoded
    @POST("token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grantType") grantType: String = "password",
        @Field("withRefreshToken") withRefreshToken: String = "true" // <- Como String
    ): TokenResponse

    @POST("google-login")
    suspend fun googleLogin(@Body body: GoogleLoginRequest): TokenResponse

    @GET("usuario-id")
    suspend fun getUsuarioId(
        @Header("Authorization") authHeader: String
    ): Response<String>


}