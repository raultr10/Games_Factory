package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.local.GamesFactoryDatabase
import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    private val localDatasource: LocalDatasource

    // Estado para el error de login (como hacías con los estados en tus otros ViewModels)
    var loginError by mutableStateOf("")

    init {
        // Inicialización idéntica a tus archivos de CarDetails/CarsViewModel
        val database = GamesFactoryDatabase.getInstance(application)
        localDatasource = LocalDatasource(
            database.usuarioDao(),
            database.empleadoDao(),
            database.productoDao(),
            database.torneoDao(),
            database.noticiaDao(),
            database.inscripcionDao()
        )
        repository = Repository(localDatasource)
    }

    fun login(dni: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            // Obtenemos la lista de usuarios del repositorio
            val users = repository.allUsuarios.first()
            val userFound = users.find { it.idDni == dni && it.contrasenaUsu == pass }

            if (userFound != null) {
                loginError = ""
                onSuccess()
            } else {
                loginError = "DNI o contraseña incorrectos"
            }
        }
    }
}