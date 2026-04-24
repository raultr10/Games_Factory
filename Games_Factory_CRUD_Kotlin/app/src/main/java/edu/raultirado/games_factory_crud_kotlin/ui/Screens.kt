package edu.raultirado.games_factory_crud_kotlin.ui

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login_screen")
    object MainScreen : Screens("main_screen")
    object VideojuegosScreen : Screens("videojuegos_list")
    object NoticiasScreen : Screens("noticias_list")
    object EmpleadosScreen : Screens("empleados_list")
    object AñadirVideojuegoScreen : Screens("añadir_videojuego_screen")
    object AñadirNoticiaScreen : Screens("añadir_noticia_screen")
    object AñadirEmpleadosScreen : Screens("añadir_empleado_screen")
}