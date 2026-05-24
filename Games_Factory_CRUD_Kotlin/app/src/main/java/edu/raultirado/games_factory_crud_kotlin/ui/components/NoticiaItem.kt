package edu.raultirado.games_factory_crud_kotlin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.raultirado.games_factory_crud_kotlin.config.AppConfig
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia

@Composable
fun NoticiaItem(noticia: Noticia, onClick: () -> Unit, onDeleteClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagen de cabecera
            val rutaImagen = "${AppConfig.URL_IMAGENES}/${noticia.imagen}"

            AsyncImage(
                model = rutaImagen,
                contentDescription = "Imagen de la noticia: ${noticia.titulo}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            // Contenido de texto con padding
            Column(modifier = Modifier.padding(16.dp)) {

                // 1. Categoría (Ahora sola arriba)
                Text(
                    text = noticia.categoriaNoticia.uppercase(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // 2. Titular
                Text(
                    text = noticia.titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 3. Descripción (Entradilla)
                Text(
                    text = noticia.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- PIE DE TARJETA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically //Alinea texto e icono verticalmente
                ) {
                    // Fecha
                    Text(
                        text = noticia.fechaCreacion,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    // Spacer flexible que empuja todo lo que viene después a la derecha
                    Spacer(modifier = Modifier.weight(1f))

                    //El icono de la papelera
                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Borrar noticia",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}