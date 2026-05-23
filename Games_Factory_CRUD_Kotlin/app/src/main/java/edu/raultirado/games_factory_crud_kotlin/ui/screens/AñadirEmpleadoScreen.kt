package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.EmpleadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirEmpleadoScreen(navController: NavController, viewModel: EmpleadosViewModel = viewModel()) {
    var dni by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var fechaNaci by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var codigoPostal by rememberSaveable { mutableStateOf("") }

    var categoria by rememberSaveable { mutableStateOf("Empleado_Normal") }
    val opcionesCategoria = listOf("Empleado_Admin", "Empleado_Normal")
    var mensajeError by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Empleado", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- SECCIÓN 1: DATOS PERSONALES ---
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Datos Personales", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormTextField(value = dni, onValueChange = { dni = it }, label = "DNI / ID:")
                    FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:")
                    FormTextField(value = apellidos, onValueChange = { apellidos = it }, label = "Apellidos:")
                    FormDatePickerField(selectedDate = fechaNaci, onDateSelected = { fechaNaci = it }, label = "Fecha de Nacimiento:")
                }
            }

            // --- SECCIÓN 2: CONTACTO ---
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Información de Contacto", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección:")
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(
                            value = telefono, onValueChange = { if (it.all { char -> char.isDigit() }) telefono = it },
                            label = "Teléfono:", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), modifier = Modifier.weight(1f)
                        )
                        FormTextField(
                            value = codigoPostal, onValueChange = { if (it.all { char -> char.isDigit() }) codigoPostal = it },
                            label = "C.P.:", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // --- SECCIÓN 3: ACCESO Y ROL ---
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Cuenta y Permisos", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormTextField(
                        value = correo, onValueChange = { correo = it }, label = "Correo Electrónico:",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    FormTextField(value = contrasena, onValueChange = { contrasena = it }, label = "Contraseña:")
                    FormDropdownField(selectedItem = categoria, onItemSelected = { categoria = it }, label = "Rol del Sistema:", options = opcionesCategoria)
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))
            }

            // --- BOTONES ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = {
                        val dniRegex = Regex("^\\d{2}\\.\\d{3}\\.\\d{3}[A-Z]\$")
                        val telRegex = Regex("^\\d{9}\$")
                        val cpRegex = Regex("^\\d{5}\$")
                        // Expresión regular corregida aquí también
                        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

                        if (!dni.matches(dniRegex)) {
                            mensajeError = "DNI inválido. Formato requerido: 21.436.587G"
                        } else if (nombre.isBlank() || apellidos.isBlank() || direccion.isBlank() || fechaNaci.isBlank() || contrasena.isBlank()) {
                            mensajeError = "Todos los campos personales son obligatorios."
                        } else if (!correo.matches(emailRegex)) {
                            mensajeError = "Introduce un correo válido con su extensión (ejemplo@dominio.com)."
                        } else if (!telefono.matches(telRegex)) {
                            mensajeError = "El teléfono debe tener exactamente 9 dígitos."
                        } else if (!codigoPostal.matches(cpRegex)) {
                            mensajeError = "El código postal debe tener exactamente 5 dígitos."
                        } else {
                            viewModel.registrarNuevoEmpleado(
                                dni, nombre, apellidos, correo, contrasena, direccion, fechaNaci, telefono, codigoPostal, categoria,
                                onSuccess = { navController.popBackStack() },
                                onError = { error -> mensajeError = error }
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("Registrar", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}