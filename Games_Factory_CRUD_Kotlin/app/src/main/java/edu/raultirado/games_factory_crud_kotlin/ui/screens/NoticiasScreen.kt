package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.Screens
import edu.raultirado.games_factory_crud_kotlin.ui.components.NoticiaItem
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.NoticiasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticiasScreen(
    navController: NavController,
    viewModel: NoticiasViewModel
) {
    val listaNoticias by viewModel.noticias.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    //Estados para borrar
    var noticiaABorrar by remember { mutableStateOf<String?>(null) }
    var mensajeErrorBorrado by remember { mutableStateOf("") }

    val noticiasFiltradas = listaNoticias.filter {
        it.titulo.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Últimas Noticias") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.AñadirNoticiaScreen.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Noticia")
            }
        }
    ) { paddingValues ->

        // --- DIÁLOGO DE CONFIRMACIÓN ---
        if (noticiaABorrar != null) {
            AlertDialog(
                onDismissRequest = { noticiaABorrar = null; mensajeErrorBorrado = "" },
                title = { Text("Confirmar eliminación") },
                text = {
                    Column {
                        Text("¿Estás seguro de que quieres borrar esta noticia?")
                        if (mensajeErrorBorrado.isNotEmpty()) {
                            Text(text = mensajeErrorBorrado, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.eliminarNoticiaExistente(
                                idNoticia = noticiaABorrar!!,
                                onSuccess = {
                                    noticiaABorrar = null
                                    mensajeErrorBorrado = ""
                                },
                                onError = { mensajeErrorBorrado = it }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Borrar", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { noticiaABorrar = null; mensajeErrorBorrado = "" }) {
                        Text("Cancelar", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar noticia por titular...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.Gray) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    error != null -> Text(text = error ?: "Error", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                    noticiasFiltradas.isEmpty() && searchQuery.isNotEmpty() -> {
                        Text(text = "No se encontraron noticias con '$searchQuery'", color = Color.Gray, modifier = Modifier.align(Alignment.Center).padding(16.dp))
                    }
                    listaNoticias.isEmpty() -> Text(text = "No hay noticias disponibles.", modifier = Modifier.align(Alignment.Center))
                    else -> {
                        LazyColumn(
                            contentPadding = PaddingValues(bottom = 80.dp, start = 16.dp, end = 16.dp, top = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(noticiasFiltradas) { noticia ->
                                NoticiaItem(
                                    noticia = noticia,
                                    onClick = { navController.navigate("${Screens.EditarNoticiaScreen.route}/${noticia.idNoticia}") },
                                    onDeleteClick = { noticiaABorrar = noticia.idNoticia }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}