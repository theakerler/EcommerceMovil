package com.proyecto.ecommercemovil.ui.screens.procesoCompra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.proyecto.ecommercemovil.data.model.direccion.Departamento
import com.proyecto.ecommercemovil.data.model.direccion.Distrito
import com.proyecto.ecommercemovil.data.model.direccion.Provincia
import com.proyecto.ecommercemovil.data.repository.direccion.cargarDistritosDesdeJsonAnidado
import com.proyecto.ecommercemovil.data.repository.direccion.cargarProvinciasDesdeJsonAnidado
import com.proyecto.ecommercemovil.data.repository.direccion.loadJsonFromAsset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DireccionScreen(
    onContinuar: () -> Unit
) {
    val context = LocalContext.current

    val departamentos = remember { loadJsonFromAsset<List<Departamento>>(context, "departamentos.json") ?: emptyList() }
    val provincias = remember { cargarProvinciasDesdeJsonAnidado(context, "provincias.json") }
    val distritos = remember { cargarDistritosDesdeJsonAnidado(context, "distritos.json") }

    var departamentoId by remember { mutableStateOf("") }
    var provinciaId by remember { mutableStateOf("") }
    var distritoId by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var detalle by remember { mutableStateOf("") }
    var guardarDatos by remember { mutableStateOf(false) }

    val provinciasFiltradas = provincias.filter { it.departamento_id == departamentoId }
    val distritosFiltrados = distritos.filter { it.provincia_id == provinciaId }

    // Variables expanded fuera del bloque
    var expandedDepartamento by remember { mutableStateOf(false) }
    var expandedProvincia by remember { mutableStateOf(false) }
    var expandedDistrito by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        Text("Datos de entrega", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))

        // Departamento
        ExposedDropdownMenuBox(
            expanded = expandedDepartamento,
            onExpandedChange = { expandedDepartamento = it }
        ) {
            OutlinedTextField(
                value = departamentos.find { it.id == departamentoId }?.nombre ?: "",
                onValueChange = {},
                label = { Text("Departamento") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clickable { expandedDepartamento = true }
            )
            ExposedDropdownMenu(
                expanded = expandedDepartamento,
                onDismissRequest = { expandedDepartamento = false }
            ) {
                departamentos.forEach { dep ->
                    DropdownMenuItem(
                        text = { Text(dep.nombre) },
                        onClick = {
                            departamentoId = dep.id
                            provinciaId = ""
                            distritoId = ""
                            expandedDepartamento = false
                            println("Departamento seleccionado: $departamentoId")

                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Provincia
        ExposedDropdownMenuBox(
            expanded = expandedProvincia,
            onExpandedChange = { expandedProvincia = it }
        ) {
            OutlinedTextField(
                value = provinciasFiltradas.find { it.id == provinciaId }?.nombre ?: "",
                onValueChange = {},
                label = { Text("Provincia") },
                readOnly = true,
                enabled = departamentoId.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clickable { if (departamentoId.isNotEmpty()) expandedProvincia = true }
            )// Justo antes del menú de provincias
            println("departamentoId actual: $departamentoId")
            println("provinciasFiltradas.size: ${provinciasFiltradas.size}")
            ExposedDropdownMenu(
                expanded = expandedProvincia,
                onDismissRequest = { expandedProvincia = false }
            ) {
                provinciasFiltradas.forEach { prov ->
                    DropdownMenuItem(
                        text = { Text(prov.nombre) },
                        onClick = {
                            provinciaId = prov.id
                            distritoId = ""
                            expandedProvincia = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Distrito
        ExposedDropdownMenuBox(
            expanded = expandedDistrito,
            onExpandedChange = { expandedDistrito = it }
        ) {

            OutlinedTextField(
                value = distritosFiltrados.find { it.id == distritoId }?.nombre ?: "",
                onValueChange = {},
                label = { Text("Distrito") },
                readOnly = true,
                enabled = provinciaId.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .clickable { if (provinciaId.isNotEmpty()) expandedDistrito = true }
            )
            ExposedDropdownMenu(
                expanded = expandedDistrito,
                onDismissRequest = { expandedDistrito = false }
            ) {
                distritosFiltrados.forEach { dist ->
                    DropdownMenuItem(
                        text = { Text(dist.nombre) },
                        onClick = {
                            distritoId = dist.id
                            expandedDistrito = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = calle,
            onValueChange = { if (it.length <= 100) calle = it },
            label = { Text("Calle") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = detalle,
            onValueChange = { if (it.length <= 200) detalle = it },
            label = { Text("Más detalles") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = guardarDatos, onCheckedChange = { guardarDatos = it })
            Text("Quisiera guardar mis datos para futuras compras.")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onContinuar,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}