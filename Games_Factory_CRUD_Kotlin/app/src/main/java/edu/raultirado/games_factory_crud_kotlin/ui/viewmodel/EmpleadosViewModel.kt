package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmpleadosViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    private val _empleados = MutableStateFlow<List<Empleado>>(emptyList())
    val empleados: StateFlow<List<Empleado>> = _empleados.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        val remoteDatasource = RemoteDatasource()
        repository = Repository(remoteDatasource)

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

    fun registrarNuevoEmpleado(
        dni: String, nombre: String, apellidos: String, correo: String,
        contrasena: String, direccion: String, fechaNaci: String,
        telefono: String, cp: String, rol: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        // Pequeña validación para que no metan datos vacíos
        if (dni.isBlank() || nombre.isBlank() || correo.isBlank() || contrasena.isBlank()) {
            onError("DNI, Nombre, Correo y Contraseña son obligatorios")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val exito = repository.registrarEmpleado(
                    dni, nombre, apellidos, correo, contrasena, direccion, fechaNaci, telefono, cp, rol
                )
                withContext(Dispatchers.Main) {
                    if (exito) {
                        fetchEmpleados() // Recargamos la lista para que aparezca el nuevo
                        onSuccess()      // Volvemos a la pantalla anterior
                    } else {
                        onError("Error al guardar. ¿Quizás el DNI ya existe?")
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