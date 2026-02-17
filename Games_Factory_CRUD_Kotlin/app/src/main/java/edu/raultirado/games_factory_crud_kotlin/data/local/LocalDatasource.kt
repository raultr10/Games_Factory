package edu.raultirado.games_factory_crud_kotlin.data.local

import edu.raultirado.games_factory_crud_kotlin.data.model.CategoriaEmpleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Producto
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import kotlinx.coroutines.flow.Flow

class LocalDatasource(
    private val usuarioDao: UsuarioDao,
    private val empleadoDao: EmpleadoDao,
    private val productoDao: ProductoDao,
    private val noticiaDao: NoticiaDao,
) {
    // --- USUARIOS ---
    fun getAllUsuarios(): Flow<List<Usuario>> = usuarioDao.getAllUsuarios()
    suspend fun insertUsuario(usuario: Usuario) = usuarioDao.insertUsuario(usuario)
    suspend fun deleteUsuario(usuario: Usuario) = usuarioDao.deleteUsuario(usuario)

    // --- PRODUCTOS (Solo la parte básica) ---
    suspend fun insertProducto(producto: Producto) {
        productoDao.insertProducto(producto)
    }

    // --- NOTICIAS ---
    fun getAllNoticias(): Flow<List<Noticia>> = noticiaDao.getAllNoticias()
    suspend fun insertNoticia(noticia: Noticia) = noticiaDao.insertNoticia(noticia)

    // --- EMPLEADOS ---
    fun getAllEmpleados(): Flow<List<Empleado>> = empleadoDao.getAllEmpleados()
    suspend fun insertEmpleado(empleado: Empleado) = empleadoDao.insertEmpleado(empleado)
    suspend fun insertCategoria(categoria: CategoriaEmpleado) = empleadoDao.insertCategoria(categoria)
    suspend fun deleteEmpleado(empleado: Empleado) = empleadoDao.deleteEmpleado(empleado)
}