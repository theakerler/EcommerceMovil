package com.proyecto.ecommercemovil.ui.screens.procesoCompra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModel
import com.proyecto.ecommercemovil.ui.theme.Red500

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.proyecto.ecommercemovil.ui.viewmodel.descuento.DescuentoViewModel
import kotlin.div
import kotlin.times

@Composable
fun CarritoScreen(
    navController: NavHostController,
    carritoId: Int,
    viewModel: CarritoViewModel,
    descuentoViewModel: DescuentoViewModel,
    usuarioId: Int
) {
    val carrito by viewModel.carrito.collectAsState()
    val error by viewModel.error.collectAsState()
    val descuentoMensaje by descuentoViewModel.mensaje.collectAsState()
    val descuento by descuentoViewModel.descuento.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var codigoDescuento by remember { mutableStateOf("") }
    var descuentoAplicado by remember { mutableStateOf(0.0) }

    // Mostrar Snackbar si hay mensaje y no hay descuento aplicado
    LaunchedEffect(descuentoMensaje, descuento) {
        val descuentoValue = descuento // almacena el valor actual
        if (descuentoMensaje != null && descuentoValue == null) {
            scope.launch {
                snackbarHostState.showSnackbar(descuentoMensaje ?: "")
            }
        }
        if (descuentoValue != null) {
            val total = carrito?.carritoItems?.sumOf { it.precioUnitario * it.cantidad } ?: 0.0
            descuentoAplicado = total * (descuentoValue.porcentaje / 100.0)
        }
    }

    LaunchedEffect(carritoId) {
        viewModel.cargarCarrito(carritoId)
    }

    if (carrito == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val total = carrito!!.carritoItems.sumOf { it.precioUnitario * it.cantidad } - descuentoAplicado

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Snackbar al inicio
            SnackbarHost(hostState = snackbarHostState)

            // ... (resto del contenido igual que antes)

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(carrito!!.carritoItems) { item ->
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
                                tint = Red500
                            )
                        }
                    }
                    Divider()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total: S/.${"%.2f".format(total.coerceAtLeast(0.0))}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Aplicar cupón ",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { showDialog = true }
                        .padding(start = 8.dp)
                )
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Código de descuento") },
                    text = {
                        OutlinedTextField(
                            value = codigoDescuento,
                            onValueChange = { codigoDescuento = it },
                            label = { Text("Código de descuento") }
                        )
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                descuentoViewModel.aplicarDescuento(usuarioId, codigoDescuento)
                                showDialog = false
                            }
                        ) {
                            Text("Aplicar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("datos_personales") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Siguiente")
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error ?: "", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}