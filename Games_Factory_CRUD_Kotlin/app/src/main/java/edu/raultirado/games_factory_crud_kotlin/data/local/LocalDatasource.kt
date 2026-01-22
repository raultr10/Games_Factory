package edu.raultirado.games_factory_crud_kotlin.data.local

import edu.raultirado.games_factory_crud_kotlin.data.model.CategoriaEmpleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Inscripcion
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Producto
import edu.raultirado.games_factory_crud_kotlin.data.model.ProductoVideojuego
import edu.raultirado.games_factory_crud_kotlin.data.model.Torneo
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import kotlinx.coroutines.flow.Flow

class LocalDatasource(
    private val usuarioDao: UsuarioDao,
    private val empleadoDao: EmpleadoDao,
    private val productoDao: ProductoDao,
    private val torneoDao: TorneoDao,
    private val noticiaDao: NoticiaDao,
    private val inscripcionDao: InscripcionDao
) {
    //Usuario
    fun getAllUsuarios(): Flow<List<Usuario>> = usuarioDao.getAllUsuarios()
    suspend fun insertUsuario(usuario: Usuario) = usuarioDao.insertUsuario(usuario)
    suspend fun deleteUsuario(usuario: Usuario) = usuarioDao.deleteUsuario(usuario)

    //Productos y videojuegos
    fun getAllVideojuegos(): Flow<List<ProductoVideojuego>> = productoDao.getProductosConVideojuegos()
    suspend fun saveVideojuegoCompleto(producto: Producto, videojuego: Videojuego) {
        productoDao.insertProducto(producto)
        productoDao.insertVideojuego(videojuego)
    }

    //Torneos
    fun getAllTorneos(): Flow<List<Torneo>> = torneoDao.getAllTorneos()
    suspend fun insertTorneo(torneo: Torneo) = torneoDao.insertTorneo(torneo)
    suspend fun deleteTorneo(torneo: Torneo) = torneoDao.deleteTorneo(torneo)

    //Inscripciones
    suspend fun insertInscripcion(dni: String, idTorneo: String) {
        inscripcionDao.insertInscripcion(Inscripcion(idDni = dni, idTorneo = idTorneo))
    }

    suspend fun deleteInscripcion(inscripcion: Inscripcion) {
        inscripcionDao.deleteInscripcion(inscripcion)
    }

    //Noticias
    fun getAllNoticias(): Flow<List<Noticia>> = noticiaDao.getAllNoticias()
    suspend fun insertNoticia(noticia: Noticia) = noticiaDao.insertNoticia(noticia)

    //Empleados
    fun getAllEmpleados(): Flow<List<Empleado>> = empleadoDao.getAllEmpleados()

    suspend fun insertEmpleado(empleado: Empleado) = empleadoDao.insertEmpleado(empleado)

    suspend fun insertCategoria(categoria: CategoriaEmpleado) = empleadoDao.insertCategoria(categoria)

    suspend fun deleteEmpleado(empleado: Empleado) = empleadoDao.deleteEmpleado(empleado)
}