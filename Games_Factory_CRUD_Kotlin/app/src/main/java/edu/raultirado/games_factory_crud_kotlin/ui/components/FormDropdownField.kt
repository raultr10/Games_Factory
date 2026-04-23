package edu.raultirado.games_factory_crud_kotlin.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdownField(
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    label: String,
    options: List<String>
) {
    // Controla si el menú está abierto o cerrado
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {}, // No dejamos que el usuario escriba, solo que seleccione
                readOnly = true,    // Hace que actúe solo como un botón
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(), // Ancla el menú a este campo de texto
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Black
                )
            )

            // El menú que se despliega
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onItemSelected(option)
                            expanded = false // Cerramos el menú al elegir
                        }
                    )
                }
            }
        }
    }
}