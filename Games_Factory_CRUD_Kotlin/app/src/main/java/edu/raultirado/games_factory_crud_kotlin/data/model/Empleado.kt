package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleado")
data class Empleado(
    @PrimaryKey(autoGenerate = true) val idEmp: Long = 0,
    val nombreEmp: String = "",
    val apellidosEmp: String = "",
    val direccion: String = "",
    val fechaNaci: String = "",
    val telefono: String = "",
    val codigoPostal: String = "",
    val correoEmp: String = "",
    val contrasenaEmp: String = "",
    val verificar: Boolean = false,
    val tipoEmpleado: String = ""
)
