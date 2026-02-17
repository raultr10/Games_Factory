package edu.raultirado.games_factory_crud_kotlin.data.repository

import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.model.CategoriaEmpleado
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

    // --- USUARIOS (Local) ---
    val allUsuarios: Flow<List<Usuario>> = localDatasource.getAllUsuarios()
    suspend fun insertUsuario(usuario: Usuario) = localDatasource.insertUsuario(usuario)
    suspend fun deleteUsuario(usuario: Usuario) = localDatasource.deleteUsuario(usuario)

    // --- VIDEOJUEGOS (Remoto - Servidor SQL) ---
    // Hemos borrado 'allVideojuegos' y 'saveVideojuegoCompleto' porque ya no usamos Room para esto.

    suspend fun fetchVideojuegos(): List<Videojuego> {
        return remoteDatasource.getVideojuegos()
    }

    // --- NOTICIAS (Local) ---
    val allNoticias: Flow<List<Noticia>> = localDatasource.getAllNoticias()
    suspend fun insertNoticia(noticia: Noticia) = localDatasource.insertNoticia(noticia)

    // --- EMPLEADOS (Local) ---
    val allEmpleados: Flow<List<Empleado>> = localDatasource.getAllEmpleados()

    suspend fun insertEmpleadoConCategoria(empleado: Empleado, categoria: CategoriaEmpleado) {
        localDatasource.insertEmpleado(empleado)
        localDatasource.insertCategoria(categoria)
    }

    suspend fun deleteEmpleado(empleado: Empleado) = localDatasource.deleteEmpleado(empleado)
}