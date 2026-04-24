package edu.raultirado.games_factory_crud_kotlin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia

@Composable
fun NoticiaItem(noticia: Noticia, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Hacemos que la tarjeta sea pulsable
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val rutaImagen = "http://192.168.1.38:8085/${noticia.imagen}"

            AsyncImage(
                model = rutaImagen,
                contentDescription = "Imagen de la noticia: ${noticia.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), // Altura fija para que todas se vean parejas
                contentScale = ContentScale.Crop
            )

            // TEXTOS DE LA NOTICIA
            Column(modifier = Modifier.padding(16.dp)) {
                // Categoría en pequeño y con color primario
                Text(
                    text = noticia.categoriaNoticia.uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Título principal
                Text(
                    text = noticia.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Descripción (cortada a 3 líneas máximo para no hacer la tarjeta gigante)
                Text(
                    text = noticia.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Fecha
                Text(
                    text = noticia.fechaCreacion,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }
        }
    }
}