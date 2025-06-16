// src/main/java/com/proyecto/ecommercemovil/EcommerceMovilApp.kt
package com.proyecto.ecommercemovil

import android.app.Application
import com.google.firebase.FirebaseApp

// En 'app/src/main/java/com/proyecto/ecommercemovil/EcommerceApp.kt'
class EcommerceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        com.proyecto.ecommercemovil.di.AppModule.init(this)
    }
}