package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoticiasViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    private val _noticias = MutableStateFlow<List<Noticia>>(emptyList())
    val noticias: StateFlow<List<Noticia>> = _noticias.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val remoteDatasource = RemoteDatasource()
        repository = Repository(remoteDatasource)

        fetchNoticias()
    }

    fun fetchNoticias() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // Llamamos a Docker
                val lista = repository.fetchNoticias()
                _noticias.value = lista
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun registrarNuevaNoticia(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (idNoticia.isBlank() || titulo.isBlank() || fechaCreacion.isBlank()) {
            onError("ID, Título y Fecha son obligatorios.")
            return
        }

        // Generamos: "Nueva Actualización" -> "nueva-actualizacion.jpg"
        val nombreImagen = titulo.lowercase().replace(" ", "-") + ".jpg"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.registrarNoticia(
                    idNoticia, titulo, descripcion, historia, fechaCreacion, categoria, nombreImagen
                )
                if (exito) {
                    fetchNoticias()
                    withContext(Dispatchers.Main) { onSuccess() }
                } else {
                    withContext(Dispatchers.Main) { onError("Error al guardar. ¿El ID ya existe?") }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError("Error: ${e.message}") }
            }
        }
    }

    fun actualizarNoticiaExistente(
        idNoticia: String, titulo: String, descripcion: String, historia: String,
        fechaCreacion: String, categoria: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (titulo.isBlank() || fechaCreacion.isBlank()) {
            onError("Título y Fecha son obligatorios.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.actualizarNoticia(idNoticia, titulo, descripcion, historia, fechaCreacion, categoria)
                if (exito) {
                    fetchNoticias()
                    withContext(Dispatchers.Main) { onSuccess() }
                } else {
                    withContext(Dispatchers.Main) { onError("Error al actualizar la base de datos.") }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError("Error: ${e.message}") }
            }
        }
    }
    fun eliminarNoticiaExistente(idNoticia: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.eliminarNoticia(idNoticia)
                withContext(Dispatchers.Main) {
                    if (exito) {
                        fetchNoticias() // Recargamos la lista
                        onSuccess()
                    } else {
                        onError("Error al eliminar la noticia.")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onError("Error de conexión: ${e.message}") }
            }
        }
    }
}