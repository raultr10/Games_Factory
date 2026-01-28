package edu.raultirado.games_factory_crud_kotlin.data.repository

import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.model.CategoriaEmpleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Producto
import edu.raultirado.games_factory_crud_kotlin.data.model.ProductoVideojuego
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import kotlinx.coroutines.flow.Flow

class Repository(private val localDatasource: LocalDatasource) {

    //Usuarios
    val allUsuarios: Flow<List<Usuario>> = localDatasource.getAllUsuarios()

    suspend fun insertUsuario(usuario: Usuario) = localDatasource.insertUsuario(usuario)

    suspend fun deleteUsuario(usuario: Usuario) = localDatasource.deleteUsuario(usuario)

    //Productos y videojuegos
    val allVideojuegos: Flow<List<ProductoVideojuego>> = localDatasource.getAllVideojuegos()

    suspend fun saveVideojuegoCompleto(producto: Producto, videojuego: Videojuego) {
        localDatasource.saveVideojuegoCompleto(producto, videojuego)
    }


    //Noticias
    val allNoticias: Flow<List<Noticia>> = localDatasource.getAllNoticias()

    suspend fun insertNoticia(noticia: Noticia) = localDatasource.insertNoticia(noticia)

    //Empleados
    val allEmpleados: Flow<List<Empleado>> = localDatasource.getAllEmpleados()

    suspend fun insertEmpleadoConCategoria(empleado: Empleado, categoria: CategoriaEmpleado) {
        localDatasource.insertEmpleado(empleado)
        localDatasource.insertCategoria(categoria)
    }

    suspend fun deleteEmpleado(empleado: Empleado) = localDatasource.deleteEmpleado(empleado)
}