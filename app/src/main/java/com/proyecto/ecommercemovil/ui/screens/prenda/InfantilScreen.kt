package com.proyecto.ecommercemovil.ui.screens.prenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.ecommercemovil.ui.theme.Red500

data class InfantilLink(val label: String, val href: String)

val ninosLinks = listOf(
    InfantilLink("Polos", "#"),
    InfantilLink("Buzo y Polera", "#"),
    InfantilLink("Bermudas", "#"),
    InfantilLink("Chalecos", "#"),
    InfantilLink("Camisas", "#"),
    InfantilLink("Calcetines y ropa interior", "#"),
    InfantilLink("Pantalones", "#"),
    InfantilLink("Ropa de baño", "#"),
    InfantilLink("Pijamas", "#"),
    InfantilLink("Chompas", "#"),
)

val ninasLinks = listOf(
    InfantilLink("Polos", "#"),
    InfantilLink("Casacas y abrigos", "#"),
    InfantilLink("Vestidos", "#"),
    InfantilLink("Chompas", "#"),
    InfantilLink("Blusas", "#"),
    InfantilLink("Buzo y Polera", "#"),
    InfantilLink("Shorts", "#"),
    InfantilLink("Chalecos", "#"),
    InfantilLink("Pantalones", "#"),
    InfantilLink("Calcetines y ropa interior", "#"),
    InfantilLink("Faldas", "#"),
    InfantilLink("Ropa de banio", "#"),
    InfantilLink("Leggings", "#"),
    InfantilLink("Pijamas", "#"),
)

@Composable
fun InfantilScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Infantil",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Niños",
            style = MaterialTheme.typography.titleMedium,
            color = Red500,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 240.dp),
            content = {
                items(ninosLinks.size) { index ->
                    val item = ninosLinks[index]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                if (item.href != "#") {
                                    navController.navigate(item.href.removePrefix("/"))
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.LightGray, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(item.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Niñas",
            style = MaterialTheme.typography.titleMedium,
            color = Red500,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 320.dp),
            content = {
                items(ninasLinks.size) { index ->
                    val item = ninasLinks[index]
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                if (item.href != "#") {
                                    navController.navigate(item.href.removePrefix("/"))
                                }
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(Color.LightGray, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(item.label, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        )
    }
}