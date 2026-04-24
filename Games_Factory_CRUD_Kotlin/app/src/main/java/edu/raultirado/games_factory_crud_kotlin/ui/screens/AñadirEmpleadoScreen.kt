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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirEmpleadoScreen(navController: NavController) {
    // ESTADOS PARA LOS CAMPOS
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var fechaNaci by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var codigoPostal by rememberSaveable { mutableStateOf("") }

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
            // Campos de texto completo
            FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:")

            FormTextField(value = apellidos, onValueChange = { apellidos = it }, label = "Apellidos:")

            FormTextField(
                value = correo,
                onValueChange = { correo = it },
                label = "Correo Electrónico:",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            FormTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección:")

            // Fecha de nacimiento con calendario
            FormDatePickerField(
                selectedDate = fechaNaci,
                onDateSelected = { fechaNaci = it },
                label = "Fecha de Nacimiento:"
            )

            // FILA PARA TELÉFONO Y CÓDIGO POSTAL (Campos pequeños)
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

            // BOTONES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Lógica Insertar más adelante */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Registrar", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}