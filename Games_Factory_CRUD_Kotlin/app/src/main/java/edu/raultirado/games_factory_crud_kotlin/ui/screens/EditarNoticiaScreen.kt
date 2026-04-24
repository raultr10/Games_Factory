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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.NoticiasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarNoticiaScreen(
    navController: NavController,
    noticiaId: String,
    viewModel: NoticiasViewModel = viewModel()
) {
    val listaNoticias by viewModel.noticias.collectAsState()
    val noticiaReal = listaNoticias.find { it.idNoticia.toString() == noticiaId }

    var isEditing by remember { mutableStateOf(false) }

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var historia by remember { mutableStateOf("") }
    var fechaCreacion by remember { mutableStateOf("") }
    var categoriaNoticia by remember { mutableStateOf("") }

    // --- SINCRONIZACIÓN ---
    LaunchedEffect(noticiaReal) {
        noticiaReal?.let {
            titulo = it.titulo
            descripcion = it.descripcion
            historia = it.historia
            fechaCreacion = it.fechaCreacion
            categoriaNoticia = it.categoriaNoticia
        }
    }

    val opcionesCategoriaNoticia = listOf("Lanzamiento", "Actualización", "Rumor", "Análisis", "Evento")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editando Noticia" else "Detalles de la Noticia") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
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
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = "Icono Galería", modifier = Modifier.size(64.dp), tint = Color.Gray)
            }

            // CAMPOS VINCULADOS A 'isEditing'
            FormTextField(value = titulo, onValueChange = { titulo = it }, label = "Título:", enabled = isEditing)

            FormTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción:",
                isSingleLine = false,
                enabled = isEditing
            )

            FormTextField(
                value = historia,
                onValueChange = { historia = it },
                label = "Historia:",
                isSingleLine = false,
                modifier = Modifier.heightIn(min = 200.dp),
                enabled = isEditing
            )

            FormDatePickerField(
                selectedDate = fechaCreacion,
                onDateSelected = { if (isEditing) fechaCreacion = it }, // Protegemos el onClick interno del calendario
                label = "Fecha Creación:"
            )

            FormDropdownField(
                selectedItem = categoriaNoticia,
                onItemSelected = { categoriaNoticia = it },
                label = "Categoría:",
                options = opcionesCategoriaNoticia,
                enabled = isEditing
            )

            // LÓGICA DE BOTONES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isEditing) {
                    Button(onClick = { isEditing = true }, modifier = Modifier.weight(1f)) {
                        Text("Editar", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                        Text("Volver", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Button(
                        onClick = { isEditing = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(
                        onClick = { isEditing = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Cancelar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}