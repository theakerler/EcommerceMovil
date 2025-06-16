package com.proyecto.ecommercemovil.ui.screens.prenda

// MujerScreen.kt
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

data class MujerLink(val label: String, val href: String)

val mujerLinks = listOf(
    MujerLink("Casacas", "/descuentos/Casacas"),
    MujerLink("Chalecos", "#"),
    MujerLink("Polos", "#"),
    MujerLink("Vestidos", "#"),
    MujerLink("Chompas", "#"),
    MujerLink("Overol y enterizos", "#"),
    MujerLink("Poleras", "#"),
    MujerLink("Shorts", "#"),
    MujerLink("Joggers y buzos", "#"),
    MujerLink("Faldas", "#"),
    MujerLink("Jeans", "#"),
    MujerLink("Abrigos y blazer", "#"),
    MujerLink("Pantalones", "#"),
    MujerLink("Ropa deportiva", "#"),
    MujerLink("Leggins", "#"),
    MujerLink("Calcetines y ropa interior", "#"),
    MujerLink("Blusas", "#"),
    MujerLink("Ropa de baño", "#"),
)
@Composable
fun MujerScreen(navController: NavController) {
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
                text = "Mujer",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleMedium,
                color = Red500
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            content = {
                items(mujerLinks.size) { index ->
                    val item = mujerLinks[index]
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