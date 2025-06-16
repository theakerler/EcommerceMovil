package com.proyecto.ecommercemovil.ui.screens.procesoCompra

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModel

@Composable
fun CarritoScreen(
    carritoId: Int,
    viewModel: CarritoViewModel
) {
    val carrito by viewModel.carrito.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cargar carrito al entrar
    LaunchedEffect(carritoId) {
        viewModel.cargarCarrito(carritoId)
    }

    if (carrito == null) {
        CircularProgressIndicator()
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        carrito!!.carritoItems.forEach { item ->
            var cantidadVisual by remember { mutableStateOf(item.cantidad) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(text = item.prenda.nombre, modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        if (cantidadVisual > 1) {
                            cantidadVisual -= 1
                            viewModel.actualizarCantidad(item.id, cantidadVisual)
                        }
                    }
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar")
                }
                Text("$cantidadVisual", modifier = Modifier.width(32.dp))
                IconButton(
                    onClick = {
                        cantidadVisual += 1
                        viewModel.actualizarCantidad(item.id, cantidadVisual)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar")
                }
            }
        }
        if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}