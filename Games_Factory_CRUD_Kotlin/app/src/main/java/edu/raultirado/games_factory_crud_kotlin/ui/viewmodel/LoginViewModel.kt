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

    // Fíjate que ahora onLoginSuccess recibe un String (el Rol)
    fun login(email: String, pass: String, onLoginSuccess: (String) -> Unit) {
        if (email.isBlank() || pass.isBlank()) {
            loginError = "Por favor, rellena todos los campos"
            return
        }

        loginError = "" // Limpiamos errores previos

        // Hacemos la llamada a la base de datos en un hilo secundario (IO)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Llamamos a la función que acabamos de crear
                val rol = repository.loginEmpleado(email, pass)

                withContext(Dispatchers.Main) {
                    if (rol != null) {
                        // ¡Login correcto! Pasamos el rol a la pantalla principal
                        onLoginSuccess(rol)
                    } else {
                        // Credenciales incorrectas
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