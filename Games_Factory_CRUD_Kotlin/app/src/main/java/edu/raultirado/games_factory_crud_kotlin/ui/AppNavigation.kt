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
    val empleadosViewModelCompartido: EmpleadosViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable("${Screens.MainScreen.route}/{rol}") { backStackEntry ->
            // Extraemos el rol que nos ha mandado el Login
            val rolUsuario = backStackEntry.arguments?.getString("rol") ?: "Empleado_Normal"

            // Se lo pasamos a la pantalla
            MainScreen(navController = navController, rol = rolUsuario)
        }

        composable(Screens.VideojuegosScreen.route) {
            val viewModel: VideojuegosViewModel = viewModel()
            VideojuegosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screens.EmpleadosScreen.route) {
            EmpleadosScreen(navController = navController, viewModel = empleadosViewModelCompartido)
        }

        composable(Screens.NoticiasScreen.route) {
            val viewModel: NoticiasViewModel = viewModel()
            NoticiasScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.AñadirVideojuegoScreen.route) {
            // Creamos una nueva pantalla composable (la definiremos más abajo)
            AñadirVideojuegoScreen(navController = navController)
        }
        composable(Screens.AñadirNoticiaScreen.route) {
            // Creamos una nueva pantalla composable (la definiremos más abajo)
            AñadirNoticiaScreen(navController = navController)
        }
        composable(Screens.AñadirEmpleadosScreen.route) {
            AñadirEmpleadoScreen(navController = navController, viewModel = empleadosViewModelCompartido)
        }
        composable("${Screens.EditarVideojuegoScreen.route}/{juegoId}") { backStackEntry ->
            // Extraemos el ID que viene en la URL
            val juegoId = backStackEntry.arguments?.getString("juegoId") ?: ""
            EditarVideojuegoScreen(navController = navController, juegoId = juegoId)
        }

        composable("${Screens.EditarNoticiaScreen.route}/{noticiaId}") { backStackEntry ->
            // Extraemos el ID que viene en la URL
            val noticiaId = backStackEntry.arguments?.getString("noticiaId") ?: ""
            EditarNoticiaScreen(navController = navController, noticiaId = noticiaId)
        }
    }
}