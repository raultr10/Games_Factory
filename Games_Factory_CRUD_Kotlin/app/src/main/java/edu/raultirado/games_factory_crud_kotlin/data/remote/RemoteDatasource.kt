package edu.raultirado.games_factory_crud_kotlin.data.remote

import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.ResultSet
import java.sql.Statement

class RemoteDatasource {

    // Función suspendida para no bloquear el hilo principal
    suspend fun getVideojuegos(): List<Videojuego> = withContext(Dispatchers.IO) {
        val lista = mutableListOf<Videojuego>()
        val connection = DbConnection.getConnection()

        if (connection != null) {
            try {
                // ¡AQUÍ ESTÁ LA CLAVE! Fíjate que V.idioma está justo antes de V.compania
                val query = """
                SELECT P.ID_producto, P.nombre_prod, P.descripcion, P.precio, P.anyo, P.imagen,
                       V.categoria_videojuego, V.tipo_consola, V.idioma, V.compania
                FROM Producto P
                INNER JOIN Videojuego V ON P.ID_producto = V.ID_producto
            """
                val stmt: Statement = connection.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    lista.add(
                        Videojuego(
                            idProducto = rs.getString("ID_producto"),
                            nombre = rs.getString("nombre_prod"),
                            descripcion = rs.getString("descripcion"),
                            precio = rs.getDouble("precio"),
                            anyo = rs.getInt("anyo"),
                            imagen = rs.getString("imagen"),
                            categoria = rs.getString("categoria_videojuego"),
                            tipoConsola = rs.getString("tipo_consola"),

                            // Si "idioma" no estaba en el SELECT de arriba, esta línea es la que hace explotar la app
                            idioma = rs.getString("idioma"),

                            compania = rs.getString("compania")
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // AÑADE ESTA LÍNEA PARA QUE EL ERROR LLEGUE A LA PANTALLA
                throw Exception("Error de SQL: ${e.message}")
            } finally {
                connection?.close()
            }
        }
        return@withContext lista
    }
    suspend fun getNoticias(): List<Noticia> = withContext(Dispatchers.IO) {
        val lista = mutableListOf<Noticia>()
        val connection = DbConnection.getConnection()

        if (connection != null) {
            try {
                // Consulta SQL para leer la tabla Noticia
                val query = "SELECT ID_noticia, titulo, descripcion, historia, fecha_creacion, categoria_noticia, imagen FROM Noticia"

                val stmt: Statement = connection.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    lista.add(
                        Noticia(
                            idNoticia = rs.getString("ID_noticia") ?: "",
                            titulo = rs.getString("titulo") ?: "",
                            descripcion = rs.getString("descripcion") ?: "",
                            historia = rs.getString("historia") ?: "",
                            fechaCreacion = rs.getString("fecha_creacion") ?: "",
                            categoriaNoticia = rs.getString("categoria_noticia") ?: "",
                            imagen = rs.getString("imagen") ?: ""
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception("Error de SQL en Noticias: ${e.message}")
            } finally {
                connection.close()
            }
        } else {
            throw Exception("No se pudo conectar al servidor de base de datos.")
        }
        return@withContext lista
    }
}