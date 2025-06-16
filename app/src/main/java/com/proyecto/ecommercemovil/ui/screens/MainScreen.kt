package com.proyecto.ecommercemovil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.proyecto.ecommercemovil.ui.components.BottomBarMixMatch
import com.proyecto.ecommercemovil.ui.components.TopAppBarMixMatch
import com.proyecto.ecommercemovil.ui.theme.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.proyecto.ecommercemovil.di.AppModule.carritoRepository
import com.proyecto.ecommercemovil.ui.components.SideMenuDrawer
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModelFactory
import kotlinx.coroutines.launch
import android.app.Application
import com.proyecto.ecommercemovil.di.AppModule.carritoApiService
@Composable
fun MainScreen(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Black)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val carritoViewModel: CarritoViewModel = viewModel(
        factory = CarritoViewModelFactory(
            application,
            carritoApiService,
            carritoRepository
        )
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            SideMenuDrawer(scope, drawerState, navController)
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBarMixMatch(
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                BottomBarMixMatch(
                    navController = navController,
                    carritoRepository = carritoRepository,
                    carritoViewModel = carritoViewModel
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                content()
            }
        }
    }
}