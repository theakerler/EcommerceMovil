package com.proyecto.ecommercemovil.ui.screens.prenda

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.proyecto.ecommercemovil.di.AppConfig
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDescuentoViewModel

@Composable
fun PrendasDescuentoScreen(
    navController: NavController,
    viewModel: PrendaDescuentoViewModel,
    categoria: String
) {
    val prendas by viewModel.prendas.collectAsState()

    LaunchedEffect(categoria) {
        viewModel.cargarPrendas(categoria)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Descuentos en $categoria", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(prendas.size) { index ->
                val prenda = prendas[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("prenda_detalle/${prenda.id}/${prenda.descuentoAplicado}")
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("${AppConfig.BASE_URL}${prenda.imagenPrincipal}"),
                        contentDescription = prenda.nombre,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(prenda.nombre, style = MaterialTheme.typography.titleMedium)
                        Text("Marca: ${prenda.marca}")
                        Text("Precio: S/ ${prenda.precio}")
                        Text("Descuento: ${prenda.descuentoAplicado}%")
                    }
                }
                Divider()
            }
        }
    }
}