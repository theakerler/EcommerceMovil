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
import kotlin.compareTo

import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import com.proyecto.ecommercemovil.ui.theme.Red500

@Composable
fun CarritoScreen(
    carritoId: Int,
    viewModel: CarritoViewModel
) {
    val carrito by viewModel.carrito.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(carritoId) {
        viewModel.cargarCarrito(carritoId)
    }

    if (carrito == null) {
        CircularProgressIndicator()
        return
    }

    Column(modifier = Modifier.padding(16.dp)) {
        carrito!!.carritoItems.forEach { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.prenda.nombre, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Talla: ${item.talla}")
                    Text(text = "Precio: S/.${item.precioUnitario}")
                    Text(text = "Subtotal: S/.${item.precioUnitario * item.cantidad}")
                }
                IconButton(
                    onClick = {
                        if (item.cantidad > 1) {
                            viewModel.actualizarCantidad(item.id, item.cantidad - 1)
                        }
                    }
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Restar")
                }
                Text("${item.cantidad}", modifier = Modifier.width(32.dp))
                IconButton(
                    onClick = {
                        viewModel.actualizarCantidad(item.id, item.cantidad + 1)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Sumar")
                }
                IconButton(
                    onClick = { viewModel.eliminarItem(item.id) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Red500 // Equivalente a Red500
                    )
                }
            }
            Divider()
        }
        if (error != null) {
            Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}