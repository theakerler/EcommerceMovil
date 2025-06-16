package com.proyecto.ecommercemovil.data.repository

import android.content.Context
import android.util.Log
import com.proyecto.ecommercemovil.data.model.GoogleLoginRequest
import com.proyecto.ecommercemovil.data.model.TokenResponse
import com.proyecto.ecommercemovil.data.network.AuthApiService

class AuthRepository(
    private val api: AuthApiService,
    private val context: Context
) {
    suspend fun login(username: String, password: String): TokenResponse {
        return api.login(username, password)
    }
    suspend fun loginWithGoogle(idToken: String, clientId: String): TokenResponse {
        val body = GoogleLoginRequest(credential = idToken, clientId = clientId)
        Log.d("GoogleLogin", "Enviando body al backend: $body")
        val response = api.googleLogin(body)
        Log.d("GoogleLogin", "Respuesta del backend: $response")
        return response
    }

    fun getToken(): String? {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        return prefs.getString("accessToken", null)
    }

    suspend fun getUsuarioId(token: String): Int {
        val response = api.getUsuarioId("Bearer $token")
        if (response.isSuccessful) {
            return response.body()?.toIntOrNull() ?: throw Exception("ID de usuario no válido")
        } else {
            throw Exception("Error al obtener usuarioId")
        }
    }
    // En AuthRepository.kt
    fun saveToken(token: String) {
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit().putString("accessToken", token).apply()
    }

}