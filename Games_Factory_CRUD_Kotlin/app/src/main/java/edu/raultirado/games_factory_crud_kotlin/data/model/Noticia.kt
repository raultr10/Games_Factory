package edu.raultirado.games_factory_crud_kotlin.data.model

data class Noticia(
    val idNoticia: String,
    val titulo: String,
    val descripcion: String,
    val historia: String,
    val fechaCreacion: String,
    val categoriaNoticia: String,
    val imagen: String
)