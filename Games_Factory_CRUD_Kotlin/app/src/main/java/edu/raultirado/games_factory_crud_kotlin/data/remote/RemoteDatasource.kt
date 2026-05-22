package edu.raultirado.games_factory_crud_kotlin.data.remote

import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.sql.ResultSet
import java.sql.Statement

class RemoteDatasource {
    suspend fun getVideojuegos(): List<Videojuego> = withContext(Dispatchers.IO) {
        val lista = mutableListOf<Videojuego>()
        val connection = DbConnection.getConnection()

        if (connection != null) {
            try {
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
    suspend fun getEmpleados(): List<Empleado> = withContext(Dispatchers.IO) {
        val lista = mutableListOf<Empleado>()
        val connection = DbConnection.getConnection()

        if (connection != null) {
            try {
                val query = "SELECT ID_emp, nombre_emp, apellidos_emp, direccion, fecha_naci, telefono, codigo_postal, correo_emp, contrasena_emp FROM Empleado"

                val stmt: Statement = connection.createStatement()
                val rs: ResultSet = stmt.executeQuery(query)

                while (rs.next()) {
                    lista.add(
                        Empleado(
                            idEmp = rs.getString("ID_emp") ?: "",
                            nombreEmp = rs.getString("nombre_emp") ?: "",
                            apellidosEmp = rs.getString("apellidos_emp") ?: "",
                            direccion = rs.getString("direccion") ?: "",
                            fechaNaci = rs.getString("fecha_naci") ?: "",
                            telefono = rs.getString("telefono") ?: "",
                            codigoPostal = rs.getString("codigo_postal") ?: "",
                            correoEmp = rs.getString("correo_emp") ?: "",
                            contrasenaEmp = rs.getString("contrasena_emp") ?: ""
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception("Error SQL Empleados: ${e.message}")
            } finally {
                connection.close()
            }
        } else {
            throw Exception("No hay conexión con la base de datos.")
        }
        return@withContext lista
    }
    private fun hashSHA256(rawData: String): String {
        val bytes = rawData.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        // Lo convertimos a Hexadecimal en MAYÚSCULAS (el equivalente a "X2")
        return digest.joinToString("") { "%02X".format(it) }
    }
    // Devuelve el rol ("Empleado_Admin" o "Empleado_Normal") o null si el login falla
    fun loginEmpleado(correo: String, contrasenaLimpia: String): String? {
        // 1. Encriptamos la contraseña tal cual se hace en tu C#
        val contrasenaEncriptada = hashSHA256(contrasenaLimpia)
        var rol: String? = null

        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                // 2. Buscamos el empleado y hacemos un JOIN con su categoría
                val query = """
                    SELECT c.tipo_empleado 
                    FROM Empleado e
                    INNER JOIN categoria_empleado c ON e.ID_emp = c.ID_emp
                    WHERE e.correo_emp = ? AND e.contrasena_emp = ?
                """

                val statement = connection.prepareStatement(query)
                statement.setString(1, correo)
                statement.setString(2, contrasenaEncriptada) // Mandamos el Hash largo

                val resultSet = statement.executeQuery()

                // Si hay resultados, significa que las credenciales son correctas
                if (resultSet.next()) {
                    rol = resultSet.getString("tipo_empleado")
                }

                resultSet.close()
                statement.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }

        return rol // Devolverá "Empleado_Admin", "Empleado_Normal" o null si se equivocó
    }
    // Añade esto en tu RemoteDatasource.kt
    fun registrarEmpleado(
        dni: String, nombre: String, apellidos: String, correo: String,
        contrasenaLimpia: String, direccion: String, fechaNaci: String,
        telefono: String, cp: String, rol: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()

        try {
            if (connection != null) {
                // 1. Desactivamos el auto-guardado para hacer una Transacción segura
                connection.autoCommit = false

                // 2. Encriptamos la contraseña con la función que hicimos antes
                val contrasenaHash = hashSHA256(contrasenaLimpia)

                val fechaArreglada = if (fechaNaci.contains("/")) {
                    val trozos = fechaNaci.split("/")
                    // Ponemos: Año - Mes - Día
                    "${trozos[2]}-${trozos[1]}-${trozos[0]}"
                } else {
                    fechaNaci
                }

                // 3. INSERTAR EN LA TABLA EMPLEADOS (¡Con los nombres corregidos!)
                val queryEmpleado = """
                    INSERT INTO Empleado 
                    (ID_emp, nombre_emp, apellidos_emp, correo_emp, contrasena_emp, direccion, fecha_naci, telefono, codigo_postal) 
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """.trimIndent()

                val stmtEmpleado = connection.prepareStatement(queryEmpleado)
                stmtEmpleado.setString(1, dni)
                stmtEmpleado.setString(2, nombre)
                stmtEmpleado.setString(3, apellidos)
                stmtEmpleado.setString(4, correo)
                stmtEmpleado.setString(5, contrasenaHash)
                stmtEmpleado.setString(6, direccion) // Corregido
                stmtEmpleado.setString(7, fechaArreglada)
                stmtEmpleado.setString(8, telefono)  // Corregido
                stmtEmpleado.setString(9, cp)        // Corregido (codigo_postal)

                stmtEmpleado.executeUpdate()
                stmtEmpleado.close()

                // 4. INSERTAR EN LA TABLA CATEGORIA_EMPLEADO
                val queryCategoria = "INSERT INTO categoria_empleado (ID_emp, tipo_empleado) VALUES (?, ?)"
                val stmtCategoria = connection.prepareStatement(queryCategoria)
                stmtCategoria.setString(1, dni)
                stmtCategoria.setString(2, rol)

                stmtCategoria.executeUpdate()
                stmtCategoria.close()

                // 5. Si todo ha ido bien, CONFIRMAMOS el guardado
                connection.commit()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Si algo falla, deshacemos los cambios
            try {
                connection?.rollback()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } finally {
            try {
                connection?.autoCommit = true
                connection?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return exito
    }
    fun registrarVideojuego(
        idProducto: String, // NUEVO: Recibimos el ID
        nombre: String, descripcion: String, precio: Double, anyo: Int,
        categoria: String, consola: String, idioma: String, compania: String,
        nombreImagen: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()

        try {
            if (connection != null) {
                connection.autoCommit = false // Empezamos la transacción

                // 1. INSERTAR EN LA TABLA PRODUCTO
                val queryProducto = """
                    INSERT INTO Producto (ID_producto, nombre_prod, descripcion, precio, anyo, imagen) 
                    VALUES (?, ?, ?, ?, ?, ?)
                """.trimIndent()

                val stmtProd = connection.prepareStatement(queryProducto)
                stmtProd.setString(1, idProducto)
                stmtProd.setString(2, nombre)
                stmtProd.setString(3, descripcion)
                stmtProd.setDouble(4, precio)
                stmtProd.setInt(5, anyo)
                stmtProd.setString(6, nombreImagen) // Guardamos "nombre-juego.jpg"

                stmtProd.executeUpdate()
                stmtProd.close()

                // 2. INSERTAR EN LA TABLA VIDEOJUEGO
                val queryVideojuego = """
                    INSERT INTO Videojuego (ID_producto, categoria_videojuego, tipo_consola, idioma, compania) 
                    VALUES (?, ?, ?, ?, ?)
                """.trimIndent()
                val stmtVid = connection.prepareStatement(queryVideojuego)
                stmtVid.setString(1, idProducto)
                stmtVid.setString(2, categoria)
                stmtVid.setString(3, consola)
                stmtVid.setString(4, idioma)
                stmtVid.setString(5, compania)

                stmtVid.executeUpdate()
                stmtVid.close()

                // Todo ha ido bien, confirmamos cambios
                connection.commit()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Si falla, deshacemos para no dejar datos huérfanos
            try { connection?.rollback() } catch (ex: Exception) {}
        } finally {
            try {
                connection?.autoCommit = true
                connection?.close()
            } catch (e: Exception) {}
        }
        return exito
    }
    fun actualizarVideojuego(
        idProducto: String, nombre: String, descripcion: String, precio: Double, anyo: Int,
        categoria: String, consola: String, idioma: String, compania: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()

        try {
            if (connection != null) {
                connection.autoCommit = false

                // 1. ACTUALIZAR TABLA PRODUCTO
                val queryProducto = """
                    UPDATE Producto 
                    SET nombre_prod = ?, descripcion = ?, precio = ?, anyo = ?
                    WHERE ID_producto = ?
                """.trimIndent()

                val stmtProd = connection.prepareStatement(queryProducto)
                stmtProd.setString(1, nombre)
                stmtProd.setString(2, descripcion)
                stmtProd.setDouble(3, precio)
                stmtProd.setInt(4, anyo)
                stmtProd.setString(5, idProducto)
                stmtProd.executeUpdate()
                stmtProd.close()

                // 2. ACTUALIZAR TABLA VIDEOJUEGO
                val queryVideojuego = """
                    UPDATE Videojuego 
                    SET categoria_videojuego = ?, tipo_consola = ?, idioma = ?, compania = ?
                    WHERE ID_producto = ?
                """.trimIndent()
                val stmtVid = connection.prepareStatement(queryVideojuego)
                stmtVid.setString(1, categoria)
                stmtVid.setString(2, consola)
                stmtVid.setString(3, idioma)
                stmtVid.setString(4, compania)
                stmtVid.setString(5, idProducto)
                stmtVid.executeUpdate()
                stmtVid.close()

                connection.commit()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try { connection?.rollback() } catch (ex: Exception) {}
        } finally {
            try {
                connection?.autoCommit = true
                connection?.close()
            } catch (e: Exception) {}
        }
        return exito
    }
    fun registrarNoticia(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String, nombreImagen: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                val query = """
                    INSERT INTO Noticia (ID_noticia, titulo, descripcion, historia, fecha_creacion, categoria_noticia, imagen) 
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """.trimIndent()
                val stmt = connection.prepareStatement(query)
                stmt.setString(1, idNoticia)
                stmt.setString(2, titulo)
                stmt.setString(3, descripcion)
                stmt.setString(4, historia)

                // Arreglamos la fecha si viene en formato DD/MM/YYYY
                val fechaArreglada = if (fechaCreacion.contains("/")) {
                    val trozos = fechaCreacion.split("/")
                    "${trozos[2]}-${trozos[1]}-${trozos[0]}"
                } else fechaCreacion

                stmt.setString(5, fechaArreglada)
                stmt.setString(6, categoria)
                stmt.setString(7, nombreImagen)

                stmt.executeUpdate()
                stmt.close()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
        return exito
    }

    fun actualizarNoticia(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                val query = """
                    UPDATE Noticia 
                    SET titulo = ?, descripcion = ?, historia = ?, fecha_creacion = ?, categoria_noticia = ?
                    WHERE ID_noticia = ?
                """.trimIndent()
                val stmt = connection.prepareStatement(query)
                stmt.setString(1, titulo)
                stmt.setString(2, descripcion)
                stmt.setString(3, historia)

                val fechaArreglada = if (fechaCreacion.contains("/")) {
                    val trozos = fechaCreacion.split("/")
                    "${trozos[2]}-${trozos[1]}-${trozos[0]}"
                } else fechaCreacion

                stmt.setString(4, fechaArreglada)
                stmt.setString(5, categoria)
                stmt.setString(6, idNoticia)

                stmt.executeUpdate()
                stmt.close()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
        return exito
    }
    fun actualizarEmpleado(
        idEmp: String, nombre: String, apellidos: String, correo: String,
        direccion: String, fechaNaci: String, telefono: String, cp: String, rol: String
    ): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                connection.autoCommit = false

                // 1. Actualizar datos en la tabla Empleado
                val queryEmp = """
                    UPDATE Empleado 
                    SET nombre_emp = ?, apellidos_emp = ?, correo_emp = ?, 
                        direccion = ?, fecha_naci = ?, telefono = ?, codigo_postal = ?
                    WHERE ID_emp = ?
                """.trimIndent()

                val stmtEmp = connection.prepareStatement(queryEmp)
                stmtEmp.setString(1, nombre)
                stmtEmp.setString(2, apellidos)
                stmtEmp.setString(3, correo)
                stmtEmp.setString(4, direccion)

                // Arreglo de fecha para SQL Server
                val fechaArreglada = if (fechaNaci.contains("/")) {
                    val trozos = fechaNaci.split("/")
                    "${trozos[2]}-${trozos[1]}-${trozos[0]}"
                } else fechaNaci
                stmtEmp.setString(5, fechaArreglada)

                stmtEmp.setString(6, telefono)
                stmtEmp.setString(7, cp)
                stmtEmp.setString(8, idEmp)
                stmtEmp.executeUpdate()
                stmtEmp.close()

                // 2. Actualizar el rol en categoria_empleado
                val queryCat = "UPDATE categoria_empleado SET tipo_empleado = ? WHERE ID_emp = ?"
                val stmtCat = connection.prepareStatement(queryCat)
                stmtCat.setString(1, rol)
                stmtCat.setString(2, idEmp)
                stmtCat.executeUpdate()
                stmtCat.close()

                connection.commit()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try { connection?.rollback() } catch (ex: Exception) {}
        } finally {
            connection?.close()
        }
        return exito
    }
    fun eliminarVideojuego(idProducto: String): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                connection.autoCommit = false

                // 1. Borramos de la tabla hija (Videojuego)
                val queryVid = "DELETE FROM Videojuego WHERE ID_producto = ?"
                val stmtVid = connection.prepareStatement(queryVid)
                stmtVid.setString(1, idProducto)
                stmtVid.executeUpdate()
                stmtVid.close()

                // 2. Borramos de la tabla padre (Producto)
                val queryProd = "DELETE FROM Producto WHERE ID_producto = ?"
                val stmtProd = connection.prepareStatement(queryProd)
                stmtProd.setString(1, idProducto)
                stmtProd.executeUpdate()
                stmtProd.close()

                connection.commit()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
            try { connection?.rollback() } catch (ex: Exception) {}
        } finally {
            try {
                connection?.autoCommit = true
                connection?.close()
            } catch (e: Exception) {}
        }
        return exito
    }

    fun eliminarNoticia(idNoticia: String): Boolean {
        var exito = false
        val connection = DbConnection.getConnection()
        try {
            if (connection != null) {
                val query = "DELETE FROM Noticia WHERE ID_noticia = ?"
                val stmt = connection.prepareStatement(query)
                stmt.setString(1, idNoticia)
                stmt.executeUpdate()
                stmt.close()
                exito = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection?.close()
        }
        return exito
    }
}