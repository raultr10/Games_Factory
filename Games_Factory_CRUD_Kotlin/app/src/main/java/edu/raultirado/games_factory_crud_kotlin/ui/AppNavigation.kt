package edu.raultirado.games_factory_crud_kotlin.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.raultirado.games_factory_crud_kotlin.ui.screens.LoginScreen
import edu.raultirado.games_factory_crud_kotlin.ui.screens.MainScreen
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            // Se inicializa solo para esta pantalla
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable(Screens.MainScreen.route) {
            MainScreen(navController)
        }
    }
}