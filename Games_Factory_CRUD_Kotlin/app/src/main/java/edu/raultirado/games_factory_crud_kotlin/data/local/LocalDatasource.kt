package edu.raultirado.games_factory_crud_kotlin.data.local

import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import kotlinx.coroutines.flow.Flow

class LocalDatasource(
    private val usuarioDao: UsuarioDao,
) {
    fun getAllUsuarios(): Flow<List<Usuario>> = usuarioDao.getAllUsuarios()
    suspend fun insertUsuario(usuario: Usuario) = usuarioDao.insertUsuario(usuario)
}