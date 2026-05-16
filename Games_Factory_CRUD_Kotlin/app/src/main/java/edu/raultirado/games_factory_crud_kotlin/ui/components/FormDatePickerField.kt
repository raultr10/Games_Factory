package edu.raultirado.games_factory_crud_kotlin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDatePickerField(
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    label: String
) {
    // Estado para mostrar u ocultar el calendario
    var showDatePicker by remember { mutableStateOf(false) }
    // Estado interno del calendario
    val datePickerState = rememberDatePickerState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        // Usamos un Box para poder hacer clic en todo el campo sin que se abra el teclado
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedDate,
                onValueChange = { },
                readOnly = true, // El usuario no puede escribir a mano
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Black
                )
            )
            // Una capa invisible por encima que captura el clic para abrir el calendario
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .clickable { showDatePicker = true }
            )
        }

        // Si showDatePicker es true, mostramos el diálogo del calendario
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        showDatePicker = false
                        // Si el usuario eligió una fecha, la formateamos a texto
                        datePickerState.selectedDateMillis?.let { millis ->
                            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            onDateSelected(format.format(Date(millis)))
                        }
                    }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancelar")
                    }
                }
            ) {
                // El componente visual del calendario
                DatePicker(state = datePickerState)
            }
        }
    }
}