package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.raultirado.games_factory_crud_kotlin.ui.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Games Factory Admin") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp), // Espacio uniforme entre tarjetas
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Panel de Gestión",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // --- TARJETA 1: VIDEOJUEGOS ---
            DashboardCard(
                title = "VIDEOJUEGOS",
                subtitle = "Gestionar catálogo y stock",
                icon = Icons.Default.VideogameAsset,
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = { navController.navigate(Screens.VideojuegosScreen.route) }
            )

            // --- TARJETA 2: EMPLEADOS ---
            DashboardCard(
                title = "EMPLEADOS",
                subtitle = "Control de personal y roles",
                icon = Icons.Default.Badge,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                onClick = { navController.navigate(Screens.EmpleadosScreen.route) }
            )

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
        modifier = Modifier.fillMaxWidth().height(130.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}