package com.proyecto.ecommercemovil.ui.navigation

import LoginScreen
import android.app.Application
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.proyecto.ecommercemovil.di.AppModule
import com.proyecto.ecommercemovil.ui.screens.MainScreen
import com.proyecto.ecommercemovil.ui.screens.botomNavScreen.HomeScreen
import com.proyecto.ecommercemovil.ui.screens.botomNavScreen.TruckScreen
import com.proyecto.ecommercemovil.ui.screens.prenda.HombreScreen
import com.proyecto.ecommercemovil.ui.screens.prenda.InfantilScreen
import com.proyecto.ecommercemovil.ui.screens.prenda.MujerScreen
import com.proyecto.ecommercemovil.ui.screens.prenda.PrendaDetalleScreen
import com.proyecto.ecommercemovil.ui.screens.prenda.PrendasDescuentoScreen
import com.proyecto.ecommercemovil.ui.screens.procesoCompra.CarritoScreen
import com.proyecto.ecommercemovil.ui.screens.sesion.LoginViewModel
import com.proyecto.ecommercemovil.ui.screens.sesion.RegisterScreen
import com.proyecto.ecommercemovil.ui.viewmodel.LoginViewModelFactory
import com.proyecto.ecommercemovil.ui.viewmodel.RegisterViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.RegisterViewModelFactory
import com.proyecto.ecommercemovil.ui.viewmodel.carrito.CarritoViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.descuento.DescuentoViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDescuentoViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDescuentoViewModelFactory
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDetalleViewModel
import com.proyecto.ecommercemovil.ui.viewmodel.prenda.PrendaDetalleViewModelFactory

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    activity: ComponentActivity
) {
    NavHost(
        navController = navController,
        startDestination = "main" // Cambia a "main" si el usuario ya está autenticado
    ) {

        composable("main") {
            MainScreen(navController) {
                Text("Contenido principal")
            }
        }

        composable("login") {
            val factory = LoginViewModelFactory(AppModule.authRepository)
            val loginViewModel: LoginViewModel = viewModel(factory = factory)
            LoginScreen(
                loginViewModel = loginViewModel,
                navController = navController,
                activity = activity,
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            // Si necesitas un factory personalizado para RegisterViewModel:
             val factory = RegisterViewModelFactory(AppModule.usuarioApiService)
             val registerViewModel: RegisterViewModel = viewModel(factory = factory)
             RegisterScreen(navController, registerViewModel)
        }
        composable("mujer") {
            MainScreen(navController) {
                MujerScreen(navController)
            }
        }
        composable("hombre") {
            MainScreen(navController) {
                HombreScreen(navController)
            }
        }
        composable("infantil") {
            MainScreen(navController) {
                InfantilScreen(navController)
            }
        }
        composable("descuentos/{categoria}") { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""
            val factory = PrendaDescuentoViewModelFactory(AppModule.prendaApiService)
            val prendaDescuentoViewModel: PrendaDescuentoViewModel = viewModel(factory = factory)

            MainScreen(navController) {
                PrendasDescuentoScreen(
                    navController = navController,
                    viewModel = prendaDescuentoViewModel,
                    categoria = categoria
                )
            }
        }

        composable("prenda_detalle/{id}/{descuento}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            val descuento = backStackEntry.arguments?.getString("descuento")?.toFloatOrNull() ?: 0f
            val factory = PrendaDetalleViewModelFactory(
                AppModule.prendaApiService,
                AppModule.authRepository,
                AppModule.carritoRepository
            )
            val viewModel: PrendaDetalleViewModel = viewModel(factory = factory)
            MainScreen(navController) {
                PrendaDetalleScreen(
                    navController = navController,
                    viewModel = viewModel,
                    prendaId = id,
                    descuento = descuento
                )
            }
        }

        composable("carrito/{carritoId}") { backStackEntry ->
            val carritoId = backStackEntry.arguments?.getString("carritoId")?.toIntOrNull() ?: return@composable
            val context = LocalContext.current
            val application = context.applicationContext as Application
            val carritoViewModel: CarritoViewModel = viewModel(
                factory = AppModule.carritoViewModelFactory(application)
            )
            val descuentoViewModel: DescuentoViewModel = viewModel(
                factory = AppModule.descuentoViewModelFactory(application)
            )

            // Estado para usuarioId
            var usuarioId by remember { mutableStateOf<Int?>(null) }

            // Obtener usuarioId usando AuthRepository
            LaunchedEffect(Unit) {
                val token = AppModule.authRepository.getToken()
                if (token != null) {
                    try {
                        usuarioId = AppModule.authRepository.getUsuarioId(token)
                    } catch (e: Exception) {
                        // Maneja el error si es necesario
                    }
                }
            }

            // Muestra un loader mientras se obtiene el usuarioId
            if (usuarioId == null) {
                androidx.compose.material3.CircularProgressIndicator()
            } else {
                MainScreen(navController) {
                    CarritoScreen(
                        carritoId = carritoId,
                        viewModel = carritoViewModel,
                        descuentoViewModel = descuentoViewModel,
                        usuarioId = usuarioId!!
                    )
                }
            }
        }


        composable("home") {
            MainScreen(navController) {
                HomeScreen()
            }
        }
        composable("envios") {
            MainScreen(navController) {
                TruckScreen()
            }
        }



        // Agrega más destinos aquí si los necesitas
    }
}