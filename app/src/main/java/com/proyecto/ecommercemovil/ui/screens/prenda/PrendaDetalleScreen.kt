package com.proyecto.ecommercemovil.ui.screens.prenda

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.proyecto.ecommercemovil.di.AppConfig
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDetalleViewModel
import kotlinx.coroutines.launch
import kotlin.times
@Composable
fun PrendaDetalleScreen(
    navController: NavController,
    viewModel: PrendaDetalleViewModel,
    prendaId: Int,
    descuento: Float
) {
    val prendaState by viewModel.prenda.collectAsState()
    var tallaSeleccionada by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(prendaId) {
        viewModel.cargarPrenda(prendaId)
    }

    prendaState?.let { prenda ->
        val precioOriginal = prenda.precio
        val precioConDescuento = if (descuento > 0) {
            precioOriginal * (1 - descuento / 100.0)
        } else {
            precioOriginal
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val imagenes = listOfNotNull(
                prenda.imagen.principal,
                prenda.imagen.hover,
                prenda.imagen.img1,
                prenda.imagen.img2
            ).map { "${AppConfig.BASE_URL}$it" }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(imagenes) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        modifier = Modifier
                            .width(250.dp)
                            .height(250.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = prenda.nombre, style = MaterialTheme.typography.titleLarge)
            Text(text = prenda.descripcion, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Precios
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (descuento > 0) {
                    Text(
                        text = "S/.${"%.2f".format(precioOriginal)}",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "S/.${"%.2f".format(precioConDescuento)}",
                        style = TextStyle(
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                } else {
                    Text(
                        text = "S/.${"%.2f".format(precioOriginal)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Text(text = "Marca: ${prenda.marca.nomMarca}")
            Text(text = "Categoría: ${prenda.categoria.nomCategoria}")
            Text(text = "Proveedor: ${prenda.proveedor.nomProveedor}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tallas disponibles:", style = MaterialTheme.typography.titleSmall)

            // Tallas como botones circulares
            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                prenda.tallas.forEach { tallaStock ->
                    val isSelected = tallaSeleccionada == tallaStock.talla.nomTalla
                    val bgColor by animateColorAsState(
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
                    )
                    val contentColor by animateColorAsState(
                        if (isSelected) Color.White else Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(bgColor)
                            .clickable { tallaSeleccionada = tallaStock.talla.nomTalla }
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tallaStock.talla.nomTalla,
                            color = contentColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón Agregar al carrito
            val enabled = tallaSeleccionada != null
            val alphaAnim by animateFloatAsState(if (enabled) 1f else 0.5f)
            Button(
                onClick = {
                    val tallaId = prenda.tallas.find { it.talla.nomTalla == tallaSeleccionada }?.talla?.id
                    if (tallaId != null) {
                        viewModel.agregarAlCarrito(
                            prendaId = prenda.id,
                            tallaId = tallaId,
                            precioConDescuento = if (descuento > 0) prenda.precio * (1 - descuento / 100.0) else prenda.precio,
                            onSuccess = { mensaje, _ ->
                                scope.launch { snackbarHostState.showSnackbar(mensaje) }
                            },
                            onError = { error ->
                                scope.launch { snackbarHostState.showSnackbar(error) }
                            }
                        )
                    }
                },
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(alphaAnim)
            ) {
                Text("Agregar al carrito")
            }
            SnackbarHost(hostState = snackbarHostState)

        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}