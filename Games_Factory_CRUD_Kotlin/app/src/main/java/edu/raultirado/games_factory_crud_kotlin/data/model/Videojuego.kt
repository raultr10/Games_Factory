package edu.raultirado.games_factory_crud_kotlin.data.model

data class Videojuego(
    val idProducto: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val anyo: Int,
    val imagen: String,
    val categoria: String,
    val tipoConsola: String,
    val idioma: String,
    val compania: String
)