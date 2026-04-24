package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormDropdownField
import edu.raultirado.games_factory_crud_kotlin.ui.components.FormTextField
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.VideojuegosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarVideojuegoScreen(
    navController: NavController,
    juegoId: String,
    viewModel: VideojuegosViewModel = viewModel()
) {
    val listaJuegos by viewModel.videojuegos.collectAsState()
    // Buscamos el juego en la lista
    val juegoReal = listaJuegos.find { it.idProducto.toString() == juegoId }

    var isEditing by remember { mutableStateOf(false) }

    // Estados vacíos al principio
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var anyo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tipoConsola by remember { mutableStateOf("") }
    var idioma by remember { mutableStateOf("") }
    var compania by remember { mutableStateOf("") }

    // --- LA MAGIA: Cuando 'juegoReal' deje de ser null, rellenamos los campos ---
    LaunchedEffect(juegoReal) {
        juegoReal?.let {
            nombre = it.nombre
            descripcion = it.descripcion
            precio = it.precio.toString()
            anyo = it.anyo.toString()
            categoria = it.categoria
            tipoConsola = it.tipoConsola
            idioma = it.idioma
            compania = it.compania
        }
    }

    // Listas para los desplegables
    val opcionesCategoria = listOf("Acción", "Aventura", "Plataformas", "RPG", "Deportes", "Shooter", "Estrategia")
    val opcionesConsola = listOf("PS5", "PS4", "Xbox Series X/S", "Nintendo Switch", "PC", "NES")
    val opcionesIdioma = listOf("Español", "Inglés", "Japonés", "Multilenguaje")
    val opcionesCompania = listOf("Sony", "Nintendo", "Microsoft", "EA", "Ubisoft", "Square Enix")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editando Videojuego" else "Detalles del Videojuego") },
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
                    .size(200.dp, 300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = "Icono Galería", modifier = Modifier.size(64.dp), tint = Color.Gray)
            }

            // FÍJATE QUE AHORA TODOS TIENEN "enabled = isEditing"
            FormTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre:", enabled = isEditing)

            FormTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = "Descripción:",
                isSingleLine = false,
                modifier = Modifier.heightIn(min = 120.dp),
                enabled = isEditing
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FormTextField(
                    value = anyo,
                    onValueChange = { if (it.all { char -> char.isDigit() }) anyo = it },
                    label = "Año:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    enabled = isEditing
                )
                FormTextField(
                    value = precio,
                    onValueChange = { if (it.isEmpty() || it.matches(Regex("""^[\d.,]*$"""))) precio = it },
                    label = "Precio:",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    enabled = isEditing
                )
            }

            FormDropdownField(selectedItem = categoria, onItemSelected = { categoria = it }, label = "Categoría:", options = opcionesCategoria, enabled = isEditing)
            FormDropdownField(selectedItem = tipoConsola, onItemSelected = { tipoConsola = it }, label = "Tipo Consola:", options = opcionesConsola, enabled = isEditing)
            FormDropdownField(selectedItem = idioma, onItemSelected = { idioma = it }, label = "Idioma:", options = opcionesIdioma, enabled = isEditing)
            FormDropdownField(selectedItem = compania, onItemSelected = { compania = it }, label = "Compañía:", options = opcionesCompania, enabled = isEditing)

            // --- LÓGICA DE LOS BOTONES DINÁMICOS ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isEditing) {
                    // MODO LECTURA: Botones "Editar" y "Volver"
                    Button(onClick = { isEditing = true }, modifier = Modifier.weight(1f)) {
                        Text("Editar", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                        Text("Volver", fontWeight = FontWeight.Bold)
                    }
                } else {
                    // MODO EDICIÓN: Botones "Guardar" y "Cancelar"
                    Button(
                        onClick = {
                            // Aquí irá la lógica para guardar en la base de datos
                            isEditing = false // Volvemos al modo lectura
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(
                        onClick = {
                            isEditing = false // Cancelamos y bloqueamos de nuevo
                            // (Opcional: aquí podríamos restaurar los valores originales si los ha cambiado)
                        },
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