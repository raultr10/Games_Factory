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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.NoticiasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirNoticiaScreen(navController: NavController, viewModel: NoticiasViewModel) {
    var idNoticia by rememberSaveable { mutableStateOf("") }
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var historia by rememberSaveable { mutableStateOf("") }
    var fechaCreacion by rememberSaveable { mutableStateOf("") }
    var categoriaNoticia by rememberSaveable { mutableStateOf("Selecciona...") }

    var mensajeError by rememberSaveable { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri = uri }
    )

    val opcionesCategoriaNoticia = listOf("Playstation", "Nintendo", "Xbox", "PC")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Redactar Noticia", fontWeight = FontWeight.Bold) },
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
            // --- SECCIÓN 1: IMAGEN DE CABECERA (Estilo Banner) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri, contentDescription = "Imagen seleccionada",
                        modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Icono Galería", modifier = Modifier.size(50.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Añadir Imagen de Portada", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // --- SECCIÓN 2: REDACCIÓN ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Contenido de la Noticia", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormTextField(value = idNoticia, onValueChange = { idNoticia = it }, label = "ID Noticia (ej: K32.564.924Q):")
                    FormTextField(value = titulo, onValueChange = { titulo = it }, label = "Titular:")
                    FormTextField(value = descripcion, onValueChange = { descripcion = it }, label = "Entradilla (Breve descripción):", isSingleLine = false)
                    FormTextField(value = historia, onValueChange = { historia = it }, label = "Cuerpo de la noticia:", isSingleLine = false, modifier = Modifier.heightIn(min = 180.dp))
                }
            }

            // --- SECCIÓN 3: METADATOS ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Publicación", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormDatePickerField(selectedDate = fechaCreacion, onDateSelected = { fechaCreacion = it }, label = "Fecha de Publicación:")
                    FormDropdownField(selectedItem = categoriaNoticia, onItemSelected = { categoriaNoticia = it }, label = "Plataforma / Categoría:", options = opcionesCategoriaNoticia)
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
                        val idRegex = Regex("^[A-Z]\\d{2}\\.\\d{3}\\.\\d{3}[A-Z]\$")

                        if (imageUri == null) {
                            mensajeError = "Debes adjuntar una imagen de portada."
                        } else if (!idNoticia.matches(idRegex)) {
                            mensajeError = "ID inválido. Formato requerido: K32.564.924Q"
                        } else if (titulo.isBlank() || descripcion.isBlank() || historia.isBlank() || fechaCreacion.isBlank()) {
                            mensajeError = "Por favor, rellena todos los campos de texto."
                        } else if (categoriaNoticia == "Selecciona...") {
                            mensajeError = "Por favor, elige una plataforma para la noticia."
                        } else {
                            viewModel.registrarNuevaNoticia(
                                idNoticia, titulo, descripcion, historia, fechaCreacion, categoriaNoticia,
                                onSuccess = { navController.popBackStack() },
                                onError = { error -> mensajeError = error }
                            )
                        }
                    },
                    modifier = Modifier.weight(1f).height(50.dp)
                ) {
                    Text("Publicar", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}