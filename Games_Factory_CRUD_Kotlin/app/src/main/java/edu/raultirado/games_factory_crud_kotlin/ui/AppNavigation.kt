package edu.raultirado.games_factory_crud_kotlin.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.raultirado.games_factory_crud_kotlin.ui.screens.AñadirEmpleadoScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.AñadirNoticiaScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.AñadirVideojuegoScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.EditarEmpleadoScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.EditarNoticiaScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.EditarVideojuegoScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.EmpleadosScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.LoginScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.MainScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.NoticiasScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.VideojuegosScreen
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.EmpleadosViewModel
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.LoginViewModel
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.NoticiasViewModel
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.VideojuegosViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // VIEWMODELS COMPARTIDOS (Se crean una sola vez)
    val empleadosViewModelCompartido: EmpleadosViewModel = viewModel()
    val videojuegosViewModelCompartido: VideojuegosViewModel = viewModel()
    val noticiasViewModelCompartido: NoticiasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable("${Screens.MainScreen.route}/{rol}") { backStackEntry ->
            val rolUsuario = backStackEntry.arguments?.getString("rol") ?: "Empleado_Normal"
            MainScreen(navController = navController, rol = rolUsuario)
        }

        composable(Screens.VideojuegosScreen.route) {
            // Le pasamos el compartido
            VideojuegosScreen(navController = navController, viewModel = videojuegosViewModelCompartido)
        }

        composable(Screens.EmpleadosScreen.route) {
            EmpleadosScreen(navController = navController, viewModel = empleadosViewModelCompartido)
        }

        composable(Screens.NoticiasScreen.route) {
            NoticiasScreen(navController = navController, viewModel = noticiasViewModelCompartido)
        }

        composable(Screens.AñadirVideojuegoScreen.route) {
            // Le pasamos el compartido
            AñadirVideojuegoScreen(navController = navController, viewModel = videojuegosViewModelCompartido)
        }

        composable(Screens.AñadirNoticiaScreen.route) {
            AñadirNoticiaScreen(navController = navController, viewModel = noticiasViewModelCompartido)
        }

        composable(Screens.AñadirEmpleadosScreen.route) {
            AñadirEmpleadoScreen(navController = navController, viewModel = empleadosViewModelCompartido)
        }

        // --- RUTAS DE EDICIÓN ---

        composable("${Screens.EditarVideojuegoScreen.route}/{juegoId}") { backStackEntry ->
            val juegoId = backStackEntry.arguments?.getString("juegoId") ?: ""
            EditarVideojuegoScreen(
                navController = navController,
                juegoId = juegoId,
                viewModel = videojuegosViewModelCompartido
            )
        }

        composable("${Screens.EditarNoticiaScreen.route}/{noticiaId}") { backStackEntry ->
            val noticiaId = backStackEntry.arguments?.getString("noticiaId") ?: ""
            EditarNoticiaScreen(
                navController = navController,
                noticiaId = noticiaId,
                viewModel = noticiasViewModelCompartido
            )
        }

        // NUEVO: Ruta para Editar Empleado con su ViewModel compartido
        composable("${Screens.EditarEmpleadoScreen.route}/{empleadoId}") { backStackEntry ->
            val empleadoId = backStackEntry.arguments?.getString("empleadoId") ?: ""
            EditarEmpleadoScreen(
                navController = navController,
                empleadoId = empleadoId,
                viewModel = empleadosViewModelCompartido
            )
        }
    }
}