package edu.raultirado.games_factory_crud_kotlin.data.repository

import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import kotlinx.coroutines.flow.Flow

class Repository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {
    val allUsuarios: Flow<List<Usuario>> = localDatasource.getAllUsuarios()
    suspend fun insertUsuario(usuario: Usuario) = localDatasource.insertUsuario(usuario)

    suspend fun fetchVideojuegos(): List<Videojuego> {
        return remoteDatasource.getVideojuegos()
    }

    suspend fun fetchNoticias(): List<Noticia> {
        return remoteDatasource.getNoticias()
    }
    suspend fun fetchEmpleados(): List<Empleado> {
        return remoteDatasource.getEmpleados()
    }
}