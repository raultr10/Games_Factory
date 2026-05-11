package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
}