package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.local.GamesFactoryDatabase
import edu.raultirado.games_factory_crud_kotlin.data.local.LocalDatasource
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Date

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
            database.noticiaDao()
        )
        repository = Repository(localDatasource)

        seedDatabase()
    }

    private fun seedDatabase() {
        viewModelScope.launch {
            // Usamos tus atributos exactos: nombreUsu, apellidosUsu, etc.
            val adminUser = Usuario(
                idDni = "admin",
                nombreUsu = "Admin",
                apellidosUsu = "Games Factory",
                direccion = "Calle Falsa 123",
                fechaNaci = Date(), // Fecha actual
                telefono = "600000000",
                codigoPostal = "03001",
                correoUsu = "admin@factory.com",
                contrasenaUsu = "1234", // Esta es la que usarás para loguearte
            )

            try {
                repository.insertUsuario(adminUser)
            } catch (e: Exception) {
                // Si el usuario ya existe (ABORT), no hacemos nada
            }
        }
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