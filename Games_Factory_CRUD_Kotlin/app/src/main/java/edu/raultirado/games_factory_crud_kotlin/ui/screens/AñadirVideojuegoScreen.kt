package edu.raultirado.games_factory_crud_kotlin.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.VideojuegosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirVideojuegoScreen(navController: NavController, viewModel: VideojuegosViewModel) {
    var idProducto by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var anyo by rememberSaveable { mutableStateOf("") }

    var categoria by rememberSaveable { mutableStateOf("Selecciona...") }
    var tipoConsola by rememberSaveable { mutableStateOf("Selecciona...") }
    var idioma by rememberSaveable { mutableStateOf("Selecciona...") }
    var compania by rememberSaveable { mutableStateOf("Selecciona...") }

    var mensajeError by rememberSaveable { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri = uri }
    )

    val opcionesCategoria = listOf("Plataforma", "Acción", "Aventura", "RPG", "Deportes", "Shooter")
    val opcionesConsola = listOf("Nintendo", "Playstation", "PC", "Xbox")
    val opcionesIdioma = listOf("SP", "IN", "JP", "ML")
    val opcionesCompania = listOf("Sony", "Nintendo", "Ubisoft", "EA", "Square Enix", "Microsoft")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Videojuego", fontWeight = FontWeight.Bold) },
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
            // --- SECCIÓN 1: FOTO (Diseño más limpio sin bordes negros duros) ---
            Box(
                modifier = Modifier
                    .size(200.dp, 280.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri, contentDescription = "Carátula",
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Añadir foto", modifier = Modifier.size(50.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Añadir Carátula", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // --- SECCIÓN 2: INFORMACIÓN GENERAL ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Información General", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormTextField(value = idProducto, onValueChange = { idProducto = it }, label = "ID Producto (ej: F28.971.452Y):")
                    FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre del juego:")
                    FormTextField(value = descripcion, onValueChange = { descripcion = it }, label = "Descripción:", isSingleLine = false, modifier = Modifier.heightIn(min = 100.dp))
                }
            }

            // --- SECCIÓN 3: DETALLES COMERCIALES ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Detalles y Clasificación", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        FormTextField(
                            value = anyo, onValueChange = { if (it.all { char -> char.isDigit() }) anyo = it },
                            label = "Año:", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.weight(1f)
                        )
                        FormTextField(
                            value = precio, onValueChange = { if (it.isEmpty() || it.matches(Regex("""^[\d.,]*$"""))) precio = it },
                            label = "Precio (€):", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.weight(1f)
                        )
                    }
                    FormDropdownField(selectedItem = categoria, onItemSelected = { categoria = it }, label = "Categoría:", options = opcionesCategoria)
                    FormDropdownField(selectedItem = tipoConsola, onItemSelected = { tipoConsola = it }, label = "Plataforma:", options = opcionesConsola)
                    FormDropdownField(selectedItem = idioma, onItemSelected = { idioma = it }, label = "Idioma:", options = opcionesIdioma)
                    FormDropdownField(selectedItem = compania, onItemSelected = { compania = it }, label = "Desarrollador:", options = opcionesCompania)
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
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
                        if (categoria == "Selecciona..." || tipoConsola == "Selecciona..." || idioma == "Selecciona..." || compania == "Selecciona...") {
                            mensajeError = "Por favor, completa todos los campos desplegables."
                        } else {
                            viewModel.registrarNuevoVideojuego(
                                idProducto, nombre, descripcion, precio, anyo, categoria, tipoConsola, idioma, compania,
                                onSuccess = { navController.popBackStack() },
                                onError = { error -> mensajeError = error }
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("Insertar", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}