package edu.raultirado.games_factory_crud_kotlin.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.raultirado.games_factory_crud_kotlin.ui.screens.AñadirNoticiaScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.AñadirVideojuegoScreen
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

    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable(Screens.MainScreen.route) {
            MainScreen(navController)
        }

        composable(Screens.VideojuegosScreen.route) {
            val viewModel: VideojuegosViewModel = viewModel()
            VideojuegosScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screens.EmpleadosScreen.route) {
            val viewModel: EmpleadosViewModel = viewModel()
            EmpleadosScreen(navController = navController, viewModel = viewModel)
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
    }
}