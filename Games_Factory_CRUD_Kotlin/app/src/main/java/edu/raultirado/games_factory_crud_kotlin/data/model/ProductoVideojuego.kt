package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductoVideojuego(
    @Embedded val producto: Producto,
    @Relation(
        parentColumn = "idProducto",
        entityColumn = "idProductoJuego"
    )
    val videojuego: Videojuego
)
