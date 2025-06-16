package com.proyecto.ecommercemovil.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.RectangleShape
import androidx.navigation.NavController
import com.proyecto.ecommercemovil.ui.components.sesion.AuthButton
import com.proyecto.ecommercemovil.ui.components.sidemenu.RedOutlinedButton
import com.proyecto.ecommercemovil.ui.theme.Red600
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun SideMenuDrawer(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController
) {
    ModalDrawerSheet {
        Text("Menú lateral", modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Ropa",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        val buttonShape = RoundedCornerShape(16.dp)
        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )

        Button(
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate("mujer")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Mujer")
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        Button(
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate("hombre")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Hombre")
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        Button(
            onClick = {
                scope.launch { drawerState.close() }
                navController.navigate("infantil")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Infantil")
        }
        Divider(modifier = Modifier.padding(horizontal = 16.dp))
        Button(
            onClick = { /* Acción para Básicos */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Básicos")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Cuenta y ayuda",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )
        AuthButton(
            scope = scope,
            drawerState = drawerState,
            navController = navController,
            buttonColors = buttonColors
        )

        Button(
            onClick = { /* Acción para Términos y condiciones */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Términos y condiciones")
        }

        Button(
            onClick = { /* Acción para Acerca de */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Acerca de")
        }
        Button(
            onClick = { /* Acción para Preguntas frecuentes */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RectangleShape,
            colors = buttonColors,
            elevation = null
        ) {
            Text("Preguntas frecuentes")
        }

        RedOutlinedButton(
            onClick = { scope.launch { drawerState.close() } },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = "Cerrar menú"
        )
    }
}

