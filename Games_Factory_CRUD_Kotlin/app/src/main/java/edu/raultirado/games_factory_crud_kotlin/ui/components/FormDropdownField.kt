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
    options: List<String>,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 4.dp))

        ExposedDropdownMenuBox(
            expanded = if (enabled) expanded else false, // Si está bloqueado, no se expande
            onExpandedChange = { if (enabled) expanded = !expanded } // Si está bloqueado, no cambia
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Black,
                    disabledTextColor = Color.DarkGray,
                    disabledBorderColor = Color.Gray,
                    disabledTrailingIconColor = Color.Gray
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onItemSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}