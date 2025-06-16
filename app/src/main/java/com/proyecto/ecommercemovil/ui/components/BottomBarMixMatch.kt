package com.proyecto.ecommercemovil.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.proyecto.ecommercemovil.data.repository.CarritoRepository
import com.proyecto.ecommercemovil.ui.theme.*
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModel
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarMixMatch(
    navController: NavController,
    carritoRepository: CarritoRepository,
    carritoViewModel: CarritoViewModel,
) {
    val items = listOf(
        Triple(Icons.Filled.Home, "Inicio", {
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
                launchSingleTop = true
            }
        }),
        Triple(Icons.Filled.ShoppingCart, "Carrito", {
            val carritoId = carritoRepository.getCarritoId()
            if (carritoId != null) {
                carritoViewModel.cargarCarrito(carritoId)
                navController.navigate("carrito/$carritoId")
            } else {
                println("No hay carritoId disponible")
            }
        }),
        Triple(Icons.Filled.LocalShipping, "Envíos", {
            navController.navigate("envios") {
                popUpTo("home")
                launchSingleTop = true
            }
        })
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Red50
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = when (index) {
                0 -> currentRoute == "home"
                1 -> currentRoute?.startsWith("carrito") == true
                2 -> currentRoute == "envios"
                else -> false
            }
            NavigationBarItem(
                icon = {
                    Icon(
                        item.first,
                        contentDescription = item.second,
                        modifier = Modifier.size(25.dp)
                    )
                },
                selected = isSelected,
                onClick = { item.third() },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Red200,
                    indicatorColor = Red500
                )
            )
        }
    }
}