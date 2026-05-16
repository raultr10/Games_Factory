package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideojuegosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    // Estado: Lista de videojuegos
    private val _videojuegos = MutableStateFlow<List<Videojuego>>(emptyList())
    val videojuegos: StateFlow<List<Videojuego>> = _videojuegos.asStateFlow()

    // Estado: Carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado: Error (opcional, para saber si falla)
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val remoteDataSource = RemoteDatasource()

        repository = Repository(remoteDataSource)

        fetchVideojuegos()
    }

    fun fetchVideojuegos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Llamada asíncrona a la base de datos
                val lista = repository.fetchVideojuegos()
                _videojuegos.value = lista
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun registrarNuevoVideojuego(
        idProducto: String, nombre: String, descripcion: String, precioStr: String, anyoStr: String,
        categoria: String, consola: String, idioma: String, compania: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Validamos que el ID también esté relleno
        if (idProducto.isBlank() || nombre.isBlank() || precioStr.isBlank() || anyoStr.isBlank()) {
            onError("ID, Nombre, precio y año son obligatorios")
            return
        }

        // GENERADOR DEL NOMBRE DE LA IMAGEN (ej: "Super Mario" -> "super-mario.jpg")
        val nombreImagen = nombre.lowercase().replace(" ", "-") + ".jpg"

        val precioSeguro = precioStr.replace(",", ".").toDoubleOrNull() ?: 0.0
        val anyoSeguro = anyoStr.toIntOrNull() ?: 2026

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.registrarVideojuego(
                    idProducto, nombre, descripcion, precioSeguro, anyoSeguro, categoria, consola, idioma, compania, nombreImagen
                )
                withContext(Dispatchers.Main) {
                    if (exito) {
                        fetchVideojuegos()
                        onSuccess()
                    } else {
                        onError("Error al guardar. ¿El ID_producto ya existe o el formato es incorrecto?")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error de conexión: ${e.message}")
                }
            }
        }
    }
    fun actualizarVideojuegoExistente(
        idProducto: String, nombre: String, descripcion: String, precioStr: String, anyoStr: String,
        categoria: String, consola: String, idioma: String, compania: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (nombre.isBlank() || precioStr.isBlank() || anyoStr.isBlank()) {
            onError("Nombre, precio y año son obligatorios")
            return
        }

        val precioSeguro = precioStr.replace(",", ".").toDoubleOrNull() ?: 0.0
        val anyoSeguro = anyoStr.toIntOrNull() ?: 2026

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.actualizarVideojuego(
                    idProducto, nombre, descripcion, precioSeguro, anyoSeguro, categoria, consola, idioma, compania
                )
                withContext(Dispatchers.Main) {
                    if (exito) {
                        fetchVideojuegos() // Recargamos la lista con los nuevos datos
                        onSuccess()
                    } else {
                        onError("Error al actualizar la base de datos.")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Error de conexión: ${e.message}")
                }
            }
        }
    }
}