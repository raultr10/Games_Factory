package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductoVideojuego(
    @Embedded val producto: Producto,
    @Relation(
        parentColumn = "ID_producto", // Nombre de la columna en la tabla Producto
        entityColumn = "ID_producto"  // Nombre de la columna en la tabla Videojuego
    )
    val videojuego: Videojuego
)