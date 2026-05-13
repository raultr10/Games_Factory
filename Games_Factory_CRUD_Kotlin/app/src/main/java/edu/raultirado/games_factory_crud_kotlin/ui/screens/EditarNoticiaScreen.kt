package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.NoticiasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarNoticiaScreen(
    navController: NavController,
    noticiaId: String,
    viewModel: NoticiasViewModel
) {
    val listaNoticias by viewModel.noticias.collectAsState()
    val noticiaReal = listaNoticias.find { it.idNoticia == noticiaId }

    var isEditing by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var historia by remember { mutableStateOf("") }
    var fechaCreacion by remember { mutableStateOf("") }
    var categoriaNoticia by remember { mutableStateOf("") }

    LaunchedEffect(noticiaReal) {
        noticiaReal?.let {
            titulo = it.titulo
            descripcion = it.descripcion
            historia = it.historia
            fechaCreacion = it.fechaCreacion
            categoriaNoticia = it.categoriaNoticia
        }
    }

    val opcionesCategoriaNoticia = listOf("Playstation", "Nintendo", "Xbox", "PC")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editando Noticia" else "Lectura de Noticia", fontWeight = FontWeight.Bold) },
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

            // --- SECCIÓN 1: BANNER DE IMAGEN ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (noticiaReal != null && noticiaReal.imagen.isNotEmpty()) {
                    val rutaLimpia = noticiaReal.imagen.removePrefix("/")
                    val urlFinal = "http://192.168.68.125:8085/$rutaLimpia"

                    AsyncImage(
                        model = urlFinal,
                        contentDescription = "Imagen de la noticia",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                }
            }

            // --- SECCIÓN 2: REDACCIÓN ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Contenido de la Noticia", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    Text("ID: $noticiaId", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium, color = Color.Gray)
                    FormTextField(value = titulo, onValueChange = { titulo = it }, label = "Titular:", enabled = isEditing)
                    FormTextField(
                        value = descripcion, onValueChange = { descripcion = it }, label = "Entradilla (Resumen):",
                        isSingleLine = false, enabled = isEditing
                    )
                    FormTextField(
                        value = historia, onValueChange = { historia = it }, label = "Cuerpo de la noticia:",
                        isSingleLine = false, modifier = Modifier.heightIn(min = 180.dp), enabled = isEditing
                    )
                }
            }

            // --- SECCIÓN 3: METADATOS ---
            ElevatedCard(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Publicación", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

                    FormDatePickerField(
                        selectedDate = fechaCreacion, onDateSelected = { if (isEditing) fechaCreacion = it },
                        label = "Fecha de Publicación:"
                    )
                    FormDropdownField(
                        selectedItem = categoriaNoticia, onItemSelected = { categoriaNoticia = it },
                        label = "Plataforma / Categoría:", options = opcionesCategoriaNoticia, enabled = isEditing
                    )
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
            }

            // --- BOTONES DINÁMICOS ---
            Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                if (!isEditing) {
                    Button(onClick = { isEditing = true }, modifier = Modifier.weight(1f).height(50.dp)) {
                        Text("EDITAR NOTICIA", fontWeight = FontWeight.Bold)
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            isEditing = false
                            mensajeError = ""
                            noticiaReal?.let {
                                titulo = it.titulo
                                descripcion = it.descripcion
                                historia = it.historia
                                fechaCreacion = it.fechaCreacion
                                categoriaNoticia = it.categoriaNoticia
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("CANCELAR", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            viewModel.actualizarNoticiaExistente(
                                idNoticia = noticiaId, titulo = titulo, descripcion = descripcion,
                                historia = historia, fechaCreacion = fechaCreacion, categoria = categoriaNoticia,
                                onSuccess = { isEditing = false; mensajeError = "" },
                                onError = { error -> mensajeError = error }
                            )
                        },
                        modifier = Modifier.weight(1f).height(50.dp)
                    ) {
                        Text("GUARDAR", fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}