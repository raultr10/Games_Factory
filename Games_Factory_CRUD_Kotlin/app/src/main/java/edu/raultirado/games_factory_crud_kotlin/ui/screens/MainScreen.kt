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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.Screens

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
                    actionIconContentColor = Color.White // Para que el icono de salir sea blanco
                ),
                // NUEVO: Añadimos el botón de cerrar sesión aquí arriba a la derecha
                actions = {
                    IconButton(onClick = {
                        // Limpiamos el historial de navegación para que no pueda volver atrás
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
            verticalArrangement = Arrangement.spacedBy(20.dp),
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
            // (Hemos eliminado el botón rojo de abajo del todo)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}