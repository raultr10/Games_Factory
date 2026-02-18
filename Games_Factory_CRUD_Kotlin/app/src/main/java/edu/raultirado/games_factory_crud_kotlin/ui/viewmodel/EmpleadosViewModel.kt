package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.local.GamesFactoryDatabase
import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmpleadosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
    val empleados: StateFlow<List<Empleado>> = _empleados.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val database = GamesFactoryDatabase.getInstance(application)
        val localDatasource = LocalDatasource(usuarioDao = database.usuarioDao())
        val remoteDatasource = RemoteDatasource()
        repository = Repository(localDatasource, remoteDatasource)

        fetchEmpleados()
    }

    fun fetchEmpleados() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val lista = repository.fetchEmpleados()
                _empleados.value = lista
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}