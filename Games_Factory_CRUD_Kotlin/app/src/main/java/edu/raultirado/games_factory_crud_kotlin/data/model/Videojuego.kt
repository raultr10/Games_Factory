package edu.raultirado.games_factory_crud_kotlin.data.model

data class Videojuego(
    val idProducto: String,

    // --- Datos que vienen de la tabla PRODUCTO ---
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val anyo: Int,
    val imagen: String, // Será la URL o nombre del archivo

    // --- Datos que vienen de la tabla VIDEOJUEGO ---
    val categoria: String,    // Usamos String en vez de Enum para evitar crasheos si la BD trae mayúsculas/minúsculas distintas
    val tipoConsola: String,
    val idioma: String,
    val compania: String
)