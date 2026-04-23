package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirVideojuegoScreen(navController: NavController) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var anyo by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }
    var tipoConsola by rememberSaveable { mutableStateOf("") }
    var idioma by rememberSaveable { mutableStateOf("") }
    var compania by rememberSaveable { mutableStateOf("") }

    val opcionesCategoria = listOf("Acción", "Aventura", "RPG", "Deportes", "Shooter", "Estrategia")
    val opcionesConsola = listOf("PS5", "PS4", "Xbox Series X/S", "Xbox One", "Nintendo Switch", "PC")
    val opcionesIdioma = listOf("Español", "Inglés", "Japonés", "Multilenguaje")
    val opcionesCompania = listOf("Sony", "Nintendo", "Microsoft", "EA", "Ubisoft", "Square Enix")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Videojuego") },
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
            Box(
                modifier = Modifier
                    .size(200.dp, 300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = "Icono Galería", modifier = Modifier.size(64.dp), tint = Color.Gray)
            }

            FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:")
            FormTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción:",
                isSingleLine = false,
                modifier = Modifier.heightIn(min = 120.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormTextField(
                    value = anyo,
                    onValueChange = { if (it.all { char -> char.isDigit() }) anyo = it },
                    label = "Año:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f) // Ocupa la mitad
                )

                FormTextField(
                    value = precio,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("""^[\d.,]*$"""))) precio = it
                    },
                    label = "Precio:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )
            }

            FormDropdownField(
                selectedItem = categoria,
                onItemSelected = { categoria = it },
                label = "Categoría:",
                options = opcionesCategoria
            )
            FormDropdownField(
                selectedItem = tipoConsola,
                onItemSelected = { tipoConsola = it },
                label = "Tipo Consola:",
                options = opcionesConsola
            )
            FormDropdownField(
                selectedItem = idioma,
                onItemSelected = { idioma = it },
                label = "Idioma:",
                options = opcionesIdioma
            )
            FormDropdownField(
                selectedItem = compania,
                onItemSelected = { compania = it },
                label = "Compañía:",
                options = opcionesCompania
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { /* Lógica Insertar */ }, modifier = Modifier.weight(1f)) {
                    Text("Insertar", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}