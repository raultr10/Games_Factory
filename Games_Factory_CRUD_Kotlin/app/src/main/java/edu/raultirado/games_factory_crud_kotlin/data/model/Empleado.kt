package edu.raultirado.games_factory_crud_kotlin.data.model

data class Empleado(
    val idEmp: String,
    val nombreEmp: String,
    val apellidosEmp: String,
    val direccion: String,
    val fechaNaci: String,
    val telefono: String,
    val codigoPostal: String,
    val correoEmp: String,
    val contrasenaEmp: String
)