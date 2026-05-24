package edu.raultirado.games_factory_crud_kotlin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.config.AppConfig
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego

@Composable
fun VideojuegoItem(juego: Videojuego, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val rutaImagen = "${AppConfig.URL_IMAGENES}/${juego.imagen}"

            AsyncImage(
                model = rutaImagen,
                contentDescription = "Carátula de ${juego.nombre}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // 1. LA CLAVE ESTÁ AQUÍ: Le damos weight(1f) a la columna
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = juego.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2, // Si es muy largo, saltará a la línea de abajo (máx 2)
                    overflow = TextOverflow.Ellipsis // Si sigue sin caber, pondrá "..."
                )
                Text(
                    text = "${juego.tipoConsola} - ${juego.compania}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${juego.precio} €",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            // 2. Quitamos el Spacer flexible que tenías aquí antes, ya no hace falta.

            // 3. El botón de borrar se quedará anclado a la derecha
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar videojuego",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}