package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Producto")
data class Producto(
    @PrimaryKey
    @ColumnInfo(name = "ID_producto")
    val idProducto: String = "",

    val descripcion: String = "",

    @ColumnInfo(name = "nombre_prod")
    val nombreProd: String = "",

    val precio: Double = 0.0,
    val anyo: Int = 0,
    val imagen: String = ""
)