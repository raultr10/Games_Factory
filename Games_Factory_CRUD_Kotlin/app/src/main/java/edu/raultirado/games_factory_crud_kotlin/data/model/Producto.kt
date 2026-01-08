package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class Producto(
    @PrimaryKey(autoGenerate = true) val idProducto: Long = 0, //Crear el ID asi (A01.123.456Z)
    val descripcion: String = "",
    val nombreProd: String = "",
    val precio: Double = 0.0,
    val anyo: Int = 0,
    val imagen: String = ""
)
