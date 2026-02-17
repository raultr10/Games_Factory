package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Noticia")
data class Noticia(
    @PrimaryKey
    @ColumnInfo(name = "ID_noticia")
    val idNoticia: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val historia: String = "",

    // CAMBIO: De Date a String
    @ColumnInfo(name = "fecha_creacion")
    val fechaCreacion: String = "",

    // CAMBIO: De Enum a String
    @ColumnInfo(name = "categoria_noticia")
    val categoriaNoticia: String = "",

    val imagen: String = ""
)