package edu.raultirado.games_factory_crud_kotlin.data.repository

import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource

class Repository(
    private val remoteDatasource: RemoteDatasource
) {
    suspend fun fetchVideojuegos(): List<Videojuego> {
        return remoteDatasource.getVideojuegos()
    }

    suspend fun fetchNoticias(): List<Noticia> {
        return remoteDatasource.getNoticias()
    }
    suspend fun fetchEmpleados(): List<Empleado> {
        return remoteDatasource.getEmpleados()
    }
    suspend fun loginEmpleado(correo: String, pass: String): String? {
        return remoteDatasource.loginEmpleado(correo, pass)
    }
    // En Repository.kt
    suspend fun registrarEmpleado(
        dni: String, nombre: String, apellidos: String, correo: String,
        contrasenaLimpia: String, direccion: String, fechaNaci: String,
        telefono: String, cp: String, rol: String
    ): Boolean {
        return remoteDatasource.registrarEmpleado(
            dni, nombre, apellidos, correo, contrasenaLimpia, direccion, fechaNaci, telefono, cp, rol
        )
    }
}