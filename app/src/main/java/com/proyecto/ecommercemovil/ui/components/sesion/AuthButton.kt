package com.proyecto.ecommercemovil.ui.components.sesion

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthButton(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    buttonColors: ButtonColors
) {
    val context = LocalContext.current
    var isLoggedIn by remember {
        mutableStateOf(
            context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .getString("jwt_token", null) != null
        )
    }

    Button(
        onClick = {
            scope.launch { drawerState.close() }
            if (isLoggedIn) {
                // Eliminar tokens
                context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE).edit()
                    .remove("jwt_token")
                    .remove("refresh_token")
                    .apply()
                isLoggedIn = false
            } else {
                navController.navigate("login")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RectangleShape,
        colors = buttonColors,
        elevation = null
    ) {
        Text(if (isLoggedIn) "Cerrar sesión" else "Iniciar sesión")
    }
}