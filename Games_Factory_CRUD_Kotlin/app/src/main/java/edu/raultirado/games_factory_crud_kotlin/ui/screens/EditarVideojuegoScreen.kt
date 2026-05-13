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
fun EditarVideojuegoScreen(
    navController: NavController,
    juegoId: String,
    viewModel: VideojuegosViewModel // Usar el compartido desde AppNavigation
) {
    val listaJuegos by viewModel.videojuegos.collectAsState()
    val juegoReal = listaJuegos.find { it.idProducto == juegoId }

    var isEditing by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var anyo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tipoConsola by remember { mutableStateOf("") }
    var idioma by remember { mutableStateOf("") }
    var compania by remember { mutableStateOf("") }

    // Rellenamos los campos cuando carga la pantalla
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

    // Opciones reales de tu base de datos
    val opcionesCategoria = listOf("Plataforma", "Acción", "Aventura", "RPG", "Deportes", "Shooter")
    val opcionesConsola = listOf("Nintendo", "Playstation", "PC", "Xbox")
    val opcionesIdioma = listOf("SP", "IN", "JP", "ML")
    val opcionesCompania = listOf("Sony", "Nintendo", "Ubisoft", "EA", "Square Enix", "Microsoft")

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

            // --- CAJA DE LA IMAGEN (Muestra la foto del servidor) ---
            Box(
                modifier = Modifier
                    .size(200.dp, 300.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (juegoReal != null && juegoReal.imagen.isNotEmpty()) {
                    AsyncImage(
                        model = "http://192.168.68.125:8085/${juegoReal.imagen}",
                        contentDescription = "Carátula del juego",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.Image, contentDescription = "Sin imagen", modifier = Modifier.size(64.dp), tint = Color.Gray)
                }
            }

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

            if (mensajeError.isNotEmpty()) {
                Text(text = mensajeError, color = Color.Red, fontWeight = FontWeight.Bold)
            }

            // --- BOTONES ---
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
                        onClick = {
                            viewModel.actualizarVideojuegoExistente(
                                idProducto = juegoId,
                                nombre = nombre,
                                descripcion = descripcion,
                                precioStr = precio,
                                anyoStr = anyo,
                                categoria = categoria,
                                consola = tipoConsola,
                                idioma = idioma,
                                compania = compania,
                                onSuccess = {
                                    isEditing = false
                                    mensajeError = ""
                                },
                                onError = { error -> mensajeError = error }
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedButton(
                        onClick = {
                            isEditing = false
                            mensajeError = ""
                            // Restauramos los valores originales al cancelar
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