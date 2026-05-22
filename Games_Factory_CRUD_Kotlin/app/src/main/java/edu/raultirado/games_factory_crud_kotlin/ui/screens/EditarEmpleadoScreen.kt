package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.EmpleadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarEmpleadoScreen(
    navController: NavController,
    empleadoId: String,
    viewModel: EmpleadosViewModel
) {
    val listaEmpleados by viewModel.empleados.collectAsState()
    val empleadoReal = listaEmpleados.find { it.idEmp == empleadoId }

    var isEditing by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var fechaNaci by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var codigoPostal by remember { mutableStateOf("") }
    var rolUsuario by remember { mutableStateOf("") }

    val opcionesRol = listOf("Empleado_Admin", "Empleado_Normal")

    // Cargar datos iniciales
    LaunchedEffect(empleadoReal) {
        empleadoReal?.let {
            nombre = it.nombreEmp
            apellidos = it.apellidosEmp
            correo = it.correoEmp
            direccion = it.direccion
            fechaNaci = it.fechaNaci
            telefono = it.telefono
            codigoPostal = it.codigoPostal
            // Asumimos que el modelo Empleado tiene el campo rol o categoría
            rolUsuario = "Empleado_Normal" // Ajustar según tu modelo
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editando Perfil" else "Ficha de Empleado", fontWeight = FontWeight.Bold) },
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

            // --- BLOQUE 1: IDENTIDAD ---
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Identidad", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                    Text("DNI: $empleadoId", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:", enabled = isEditing)
                    FormTextField(value = apellidos, onValueChange = { apellidos = it }, label = "Apellidos:", enabled = isEditing)
                    FormDatePickerField(selectedDate = fechaNaci, onDateSelected = { if(isEditing) fechaNaci = it }, label = "Nacimiento:")
                }
            }

            // --- BLOQUE 2: CONTACTO ---
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Contacto", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                    FormTextField(value = correo, onValueChange = { correo = it }, label = "Email:", enabled = isEditing)
                    FormTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección:", enabled = isEditing)
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(value = telefono, onValueChange = { telefono = it }, label = "Tel.:", modifier = Modifier.weight(1f), enabled = isEditing)
                        FormTextField(value = codigoPostal, onValueChange = { codigoPostal = it }, label = "C.P.:", modifier = Modifier.weight(1f), enabled = isEditing)
                    }
                }
            }

            // --- BLOQUE 3: SISTEMA ---
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Permisos", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    FormDropdownField(selectedItem = rolUsuario, onItemSelected = { rolUsuario = it }, label = "Rol:", options = opcionesRol, enabled = isEditing)
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }

            // --- BOTONES DINÁMICOS ---
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (!isEditing) {
                    Button(onClick = { isEditing = true }, modifier = Modifier.weight(1f).height(50.dp)) {
                        Text("EDITAR DATOS")
                    }
                } else {
                    OutlinedButton(onClick = { isEditing = false }, modifier = Modifier.weight(1f).height(50.dp)) {
                        Text("CANCELAR")
                    }
                    Button(
                        onClick = {
                            val telRegex = Regex("^\\d{9}\$")
                            val cpRegex = Regex("^\\d{5}\$")
                            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")

                            if (nombre.isBlank() || apellidos.isBlank() || direccion.isBlank() || fechaNaci.isBlank()) {
                                mensajeError = "No puedes dejar campos personales en blanco."
                            } else if (!correo.matches(emailRegex)) {
                                mensajeError = "Introduce un correo válido con su extensión (ejemplo@dominio.com)."
                            } else if (!telefono.matches(telRegex)) {
                                mensajeError = "El teléfono debe tener exactamente 9 dígitos."
                            } else if (!codigoPostal.matches(cpRegex)) {
                                mensajeError = "El código postal debe tener exactamente 5 dígitos."
                            } else {
                                // 1. ¡LA CLAVE ESTÁ AQUÍ! Limpiamos el error de validación local antes de lanzar la petición
                                mensajeError = ""

                                viewModel.actualizarEmpleadoExistente(
                                    idEmp = empleadoId, nombre = nombre, apellidos = apellidos, correo = correo,
                                    direccion = direccion, fechaNaci = fechaNaci, telefono = telefono, cp = codigoPostal, rol = rolUsuario,
                                    onSuccess = {
                                        isEditing = false
                                        // 2. También lo limpiamos aquí para que al salir del modo edición quede impecable
                                        mensajeError = ""
                                    },
                                    onError = { mensajeError = it }
                                )
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) {
                        Text("GUARDAR")
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}