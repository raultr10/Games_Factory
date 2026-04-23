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
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDatePickerField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AñadirNoticiaScreen(navController: NavController) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var historia by rememberSaveable { mutableStateOf("") }
    var fechaCreacion by rememberSaveable { mutableStateOf("") }
    var categoriaNoticia by rememberSaveable { mutableStateOf("") }

    val opcionesCategoriaNoticia = listOf("Lanzamiento", "Actualización", "Rumor", "Análisis", "Evento")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicar Nueva Noticia") },
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
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = "Icono Galería", modifier = Modifier.size(64.dp), tint = Color.Gray)
            }

            FormTextField(value = titulo, onValueChange = { titulo = it }, label = "Título:")

            FormTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción:",
                isSingleLine = false
            )

            FormTextField(
                value = historia,
                onValueChange = { historia = it },
                label = "Historia:",
                isSingleLine = false,
                modifier = Modifier.heightIn(min = 200.dp)
            )

            FormDatePickerField(
                selectedDate = fechaCreacion,
                onDateSelected = { fechaCreacion = it },
                label = "Fecha Creación:"
            )

            FormDropdownField(
                selectedItem = categoriaNoticia,
                onItemSelected = { categoriaNoticia = it },
                label = "Categoría:",
                options = opcionesCategoriaNoticia
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