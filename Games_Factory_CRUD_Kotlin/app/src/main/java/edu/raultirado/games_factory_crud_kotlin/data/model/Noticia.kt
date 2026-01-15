package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class CatNoticia { Playstation, Nintendo, Xbox, PC }

@Entity(tableName = "Noticia")
data class Noticia(
    @PrimaryKey
    @ColumnInfo(name = "ID_noticia")
    val idNoticia: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val historia: String = "",

    @ColumnInfo(name = "fecha_creacion")
    val fechaCreacion: Date,

    @ColumnInfo(name = "categoria_noticia")
    val categoriaNoticia: CatNoticia,

    val imagen: String = ""
)