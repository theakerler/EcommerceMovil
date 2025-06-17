package com.proyecto.ecommercemovil.di

import android.app.Application
import com.proyecto.ecommercemovil.data.network.AuthApiService
import com.proyecto.ecommercemovil.data.network.CarritoApiService
import com.proyecto.ecommercemovil.data.network.PrendaApiService
import com.proyecto.ecommercemovil.data.network.UsuarioApiService
import com.proyecto.ecommercemovil.data.repository.AuthRepository
import com.proyecto.ecommercemovil.data.repository.CarritoRepository
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppModule {
    private const val BASE_URL = "http://192.168.106.135:8080/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val usuarioApiService: UsuarioApiService = retrofit.create(UsuarioApiService::class.java)
    val prendaApiService: PrendaApiService = retrofit.create(PrendaApiService::class.java)
    val carritoApiService: CarritoApiService = retrofit.create(CarritoApiService::class.java)

    lateinit var authRepository: AuthRepository
    lateinit var carritoRepository: CarritoRepository

    // Agrega la factory del ViewModel de carrito
    val carritoViewModelFactory: (Application) -> CarritoViewModelFactory = { application ->
        CarritoViewModelFactory(application, carritoApiService, carritoRepository)
    }
    fun init(context: android.content.Context) {
        authRepository = AuthRepository(authApiService, context)
        carritoRepository = CarritoRepository(carritoApiService, context)
    }
}