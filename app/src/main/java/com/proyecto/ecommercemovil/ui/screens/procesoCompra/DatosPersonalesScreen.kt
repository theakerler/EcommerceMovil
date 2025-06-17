package com.proyecto.ecommercemovil.ui.screens.procesoCompra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.proyecto.ecommercemovil.ui.theme.Red500

@Composable
fun DatosPersonalesScreen(navController: NavHostController) {
    var correo by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var guardarDatos by remember { mutableStateOf(false) }
    var deseaFactura by remember { mutableStateOf(false) }
    var recibirPromos by remember { mutableStateOf(false) }
    var aceptaTerminos by remember { mutableStateOf(false) }

    val correoValido = correo.length <= 50 && android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    val nombreValido = nombre.length in 1..50
    val apellidoValido = apellido.length in 1..50
    val dniValido = dni.length == 8 && dni.all { it.isDigit() }
    val telefonoValido = telefono.length == 9 && telefono.startsWith("9") && telefono.all { it.isDigit() }
    val puedeContinuar = correoValido && nombreValido && apellidoValido && dniValido && telefonoValido && aceptaTerminos

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Datos Personales", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = correo,
                onValueChange = { if (it.length <= 50) correo = it },
                label = { Text("Correo") },
                isError = !correoValido && correo.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = nombre,
                onValueChange = { if (it.length <= 50) nombre = it },
                label = { Text("Nombre") },
                isError = !nombreValido && nombre.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = apellido,
                onValueChange = { if (it.length <= 50) apellido = it },
                label = { Text("Apellido") },
                isError = !apellidoValido && apellido.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = dni,
                onValueChange = { if (it.length <= 8 && it.all { c -> c.isDigit() }) dni = it },
                label = { Text("Documento de Identidad") },
                isError = !dniValido && dni.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            OutlinedTextField(
                value = telefono,
                onValueChange = { if (it.length <= 9 && it.all { c -> c.isDigit() }) telefono = it },
                label = { Text("Teléfono / Móvil") },
                isError = !telefonoValido && telefono.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(Modifier.height(8.dp)) }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = guardarDatos, onCheckedChange = { guardarDatos = it })
                Text("Quisiera guardar mis datos para futuras compras")
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = deseaFactura, onCheckedChange = { deseaFactura = it })
                Text("Deseo Factura")
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = recibirPromos, onCheckedChange = { recibirPromos = it })
                Text("Quiero recibir novedades y promociones")
            }
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(checked = aceptaTerminos, onCheckedChange = { aceptaTerminos = it })
                Text(
                    buildAnnotatedString {
                        append("Acepto los ")
                        pushStringAnnotation(tag = "terminos", annotation = "terminos")
                        withStyle(SpanStyle(color = Red500)) { append("Términos y Condiciones") }
                        pop()
                        append(" y ")
                        pushStringAnnotation(tag = "politica", annotation = "politica")
                        withStyle(SpanStyle(color = Red500)) { append("Política de protección de datos personales") }
                        pop()
                    },
                    modifier = Modifier
                        .clickable { /* Maneja el click si lo deseas */ }
                )
            }
        }
        item { Spacer(Modifier.height(16.dp)) }
        item {
            Button(
                onClick = { navController.navigate("direccion") },
                enabled = puedeContinuar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Siguiente")
            }
        }
    }
}