package edu.raultirado.games_factory_crud_kotlin.ui

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login_screen")
    object MainScreen : Screens("main_screen")
    object VideojuegosScreen : Screens("videojuegos_list")
    object NoticiasScreen : Screens("noticias_list")
    object EmpleadosScreen : Screens("empleados_list")
}