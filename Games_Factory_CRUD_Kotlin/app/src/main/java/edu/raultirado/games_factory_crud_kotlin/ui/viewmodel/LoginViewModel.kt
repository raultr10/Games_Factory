package edu.raultirado.games_factory_crud_kotlin.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.raultirado.games_factory_crud_kotlin.data.remote.RemoteDatasource
import edu.raultirado.games_factory_crud_kotlin.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    var loginError by mutableStateOf("")
        private set

    private val repository = Repository(RemoteDatasource())

    fun login(email: String, pass: String, onLoginSuccess: (String) -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            loginError = "Por favor, rellena todos los campos"
            return
        }

        loginError = ""

        // Hacemos la llamada a la base de datos en un hilo secundario (IO)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Llamamos a la función que acabamos de crear
                val rol = repository.loginEmpleado(email, pass)

                withContext(Dispatchers.Main) {
                    if (rol != null) {
                        onLoginSuccess(rol)
                    } else {
                        loginError = "Correo o contraseña incorrectos"
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loginError = "Error de conexión con el servidor"
                    e.printStackTrace()
                }
            }
        }
    }
}