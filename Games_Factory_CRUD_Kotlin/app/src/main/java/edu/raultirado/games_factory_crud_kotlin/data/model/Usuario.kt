package edu.raultirado.games_factory_crud_kotlin.data.model

import java.util.Date

data class Usuario(
    val idDni: String,
    val nombreUsu: String,
    val apellidosUsu: String,
    val direccion: String,
    val fechaNaci: Date,
    val fechaN: String,
    val telefono: String,
    val codigoPostal: String,
    val correoUsu: String,
    val contrasenaUsu: String,
    val verificar: Boolean
)
