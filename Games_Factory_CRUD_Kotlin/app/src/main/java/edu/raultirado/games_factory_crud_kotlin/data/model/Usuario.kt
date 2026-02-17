package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Usuario")
data class Usuario(
    @PrimaryKey
    @ColumnInfo(name = "ID_DNI")
    val idDni: String = "",

    @ColumnInfo(name = "nombre_usu")
    val nombreUsu: String = "",

    @ColumnInfo(name = "apellidos_usu")
    val apellidosUsu: String = "",

    val direccion: String = "",

    // CAMBIO: De Date a String
    @ColumnInfo(name = "fecha_naci")
    val fechaNaci: String = "",

    val telefono: String = "",

    @ColumnInfo(name = "codigo_postal")
    val codigoPostal: String = "",

    @ColumnInfo(name = "correo_usu")
    val correoUsu: String = "",

    @ColumnInfo(name = "contrasena_usu")
    val contrasenaUsu: String = "",
)