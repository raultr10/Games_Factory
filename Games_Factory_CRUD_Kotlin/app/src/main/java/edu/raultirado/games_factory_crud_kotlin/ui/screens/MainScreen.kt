package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.Screens
import edu.raultirado.games_factory_crud_kotlin.ui.components.DashboardCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, rol: String) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Games Factory",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screens.LoginScreen.route) {
                            popUpTo(0)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Cerrar Sesión"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp), // Un poco más de espacio entre tarjetas
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // --- CABECERA Y CHIP DE ROL ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Panel de Gestión",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    color = if (rol == "Empleado_Admin") MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = if (rol == "Empleado_Admin") "Modo Administrador" else "Modo Empleado",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (rol == "Empleado_Admin") MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- TARJETA 1: VIDEOJUEGOS ---
            DashboardCard(
                title = "VIDEOJUEGOS",
                subtitle = "Gestionar catálogo y stock",
                icon = Icons.Default.VideogameAsset,
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = { navController.navigate(Screens.VideojuegosScreen.route) }
            )

            // --- TARJETA 2: EMPLEADOS (SOLO ADMIN) ---
            if (rol == "Empleado_Admin") {
                DashboardCard(
                    title = "EMPLEADOS",
                    subtitle = "Control de personal y roles",
                    icon = Icons.Default.Badge,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    onClick = { navController.navigate(Screens.EmpleadosScreen.route) }
                )
            }

            // --- TARJETA 3: NOTICIAS ---
            DashboardCard(
                title = "NOTICIAS",
                subtitle = "Publicar novedades en la web",
                icon = Icons.Default.Newspaper,
                color = MaterialTheme.colorScheme.secondaryContainer,
                onClick = { navController.navigate(Screens.NoticiasScreen.route) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}