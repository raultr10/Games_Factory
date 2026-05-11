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
    // ESTADOS PARA LOS CAMPOS
    var dni by rememberSaveable { mutableStateOf("") } // NUEVO: ID_emp
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") } // NUEVO: Contraseña
    var direccion by rememberSaveable { mutableStateOf("") }
    var fechaNaci by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var codigoPostal by rememberSaveable { mutableStateOf("") }

    // NUEVO: Categoría del empleado
    var categoria by rememberSaveable { mutableStateOf("Empleado_Normal") }
    val opcionesCategoria = listOf("Empleado_Admin", "Empleado_Normal")
    var mensajeError by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Empleado") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
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
            // --- NUEVO: Fila para DNI y Categoría ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextField(
                    value = dni,
                    onValueChange = { dni = it },
                    label = "DNI / ID:",
                    modifier = Modifier.weight(1f)
                )
                Box(modifier = Modifier.weight(1f)) {
                    FormDropdownField(
                        selectedItem = categoria,
                        onItemSelected = { categoria = it },
                        label = "Rol:",
                        options = opcionesCategoria
                    )
                }
            }

            FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:")
            FormTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = "Apellidos:"
            )

            // --- NUEVO: Fila para Correo y Contraseña ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = "Correo:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.weight(1f)
                )
                FormTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = "Contraseña:",
                    modifier = Modifier.weight(1f)
                )
            }

            FormTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = "Dirección:"
            )

            FormDatePickerField(
                selectedDate = fechaNaci,
                onDateSelected = { fechaNaci = it },
                label = "Fecha de Nacimiento:"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextField(
                    value = telefono,
                    onValueChange = { if (it.all { char -> char.isDigit() }) telefono = it },
                    label = "Teléfono:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f)
                )

                FormTextField(
                    value = codigoPostal,
                    onValueChange = { if (it.all { char -> char.isDigit() }) codigoPostal = it },
                    label = "C.P.:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = Color.Red, fontWeight = FontWeight.Bold)
            }

            // BOTONES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        // LLAMAMOS AL VIEWMODEL AL PULSAR
                        viewModel.registrarNuevoEmpleado(
                            dni,
                            nombre,
                            apellidos,
                            correo,
                            contrasena,
                            direccion,
                            fechaNaci,
                            telefono,
                            codigoPostal,
                            categoria,
                            onSuccess = {
                                // Si va bien, volvemos a la lista
                                navController.popBackStack()
                            },
                            onError = { error ->
                                // Si va mal, mostramos el texto rojo
                                mensajeError = error
                            }
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Registrar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}