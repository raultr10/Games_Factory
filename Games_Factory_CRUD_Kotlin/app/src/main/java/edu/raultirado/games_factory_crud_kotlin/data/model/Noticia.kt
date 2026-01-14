package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// Enum para la categoría de la noticia
enum class CatNoticia { Playstation, Nintendo, Xbox, PC }

@Entity(tableName = "Noticia")
data class Noticia(
    @PrimaryKey
    val idNoticia: String,
    val titulo: String,
    val descripcion: String,
    val historia: String,
    val fechaCreacion: Date,
    val categoriaNoticia: CatNoticia,
    val imagen: String
)