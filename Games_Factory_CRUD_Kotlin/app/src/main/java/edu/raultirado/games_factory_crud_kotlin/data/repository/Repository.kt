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
    suspend fun registrarVideojuego(
        idProducto: String, nombre: String, descripcion: String, precio: Double, anyo: Int,
        categoria: String, consola: String, idioma: String, compania: String, nombreImagen: String
    ): Boolean {
        return remoteDatasource.registrarVideojuego(
            idProducto, nombre, descripcion, precio, anyo, categoria, consola, idioma, compania, nombreImagen
        )
    }
    suspend fun actualizarVideojuego(
        idProducto: String, nombre: String, descripcion: String, precio: Double, anyo: Int,
        categoria: String, consola: String, idioma: String, compania: String
    ): Boolean {
        return remoteDatasource.actualizarVideojuego(
            idProducto, nombre, descripcion, precio, anyo, categoria, consola, idioma, compania
        )
    }
    suspend fun registrarNoticia(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String, nombreImagen: String
    ): Boolean {
        return remoteDatasource.registrarNoticia(idNoticia, titulo, descripcion, historia, fechaCreacion, categoria, nombreImagen)
    }

    suspend fun actualizarNoticia(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String
    ): Boolean {
        return remoteDatasource.actualizarNoticia(idNoticia, titulo, descripcion, historia, fechaCreacion, categoria)
    }
    suspend fun actualizarEmpleado(
        idEmp: String, nombre: String, apellidos: String, correo: String,
        direccion: String, fechaNaci: String, telefono: String, cp: String, rol: String
    ): Boolean = remoteDatasource.actualizarEmpleado(idEmp, nombre, apellidos, correo, direccion, fechaNaci, telefono, cp, rol)

    suspend fun eliminarVideojuego(idProducto: String): Boolean {
        return remoteDatasource.eliminarVideojuego(idProducto)
    }

    suspend fun eliminarNoticia(idNoticia: String): Boolean {
        return remoteDatasource.eliminarNoticia(idNoticia)
    }
}