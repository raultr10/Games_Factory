package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.Screens
import edu.raultirado.games_factory_crud_kotlin.ui.components.EmpleadoItem
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.EmpleadosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpleadosScreen(
    navController: NavController,
    viewModel: EmpleadosViewModel
) {
    val listaEmpleados by viewModel.empleados.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantilla de Empleados") },
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
                onClick = { navController.navigate(Screens.AñadirEmpleadosScreen.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Empleado")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text(text = error ?: "Error", color = Color.Red, modifier = Modifier.align(Alignment.Center))
                listaEmpleados.isEmpty() -> Text(text = "No hay empleados registrados.", modifier = Modifier.align(Alignment.Center))
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(listaEmpleados) { empleado ->
                            // --- LA MAGIA ESTÁ AQUÍ ---
                            // Envolvemos el item en un Box para que detecte el clic en toda la tarjeta
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // Navegamos pasando el ID del empleado seleccionado
                                        navController.navigate("${Screens.EditarEmpleadoScreen.route}/${empleado.idEmp}")
                                    }
                            ) {
                                EmpleadoItem(empleado)
                            }
                        }
                    }
                }
            }
        }
    }
}