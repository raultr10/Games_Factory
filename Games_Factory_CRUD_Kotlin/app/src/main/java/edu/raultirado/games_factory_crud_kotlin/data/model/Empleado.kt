package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Empleado")
data class Empleado(
    @PrimaryKey
    @ColumnInfo(name = "ID_emp")
    val idEmp: String = "",

    @ColumnInfo(name = "nombre_emp")
    val nombreEmp: String = "",

    @ColumnInfo(name = "apellidos_emp")
    val apellidosEmp: String = "",

    val direccion: String = "",

    // CAMBIO: De Date a String
    @ColumnInfo(name = "fecha_naci")
    val fechaNaci: String = "",

    val telefono: String = "",

    @ColumnInfo(name = "codigo_postal")
    val codigoPostal: String = "",

    @ColumnInfo(name = "correo_emp")
    val correoEmp: String = "",

    @ColumnInfo(name = "contrasena_emp")
    val contrasenaEmp: String = ""
)