package edu.raultirado.games_factory_crud_kotlin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.raultirado.games_factory_crud_kotlin.ui.Screens
import edu.raultirado.games_factory_crud_kotlin.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    // Surface actúa como fondo principal de la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp), // Más margen para que no se pegue a los bordes
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // --- LOGO (Icono) ---
            Image(
                painter = painterResource(id = edu.raultirado.games_factory_crud_kotlin.R.drawable.logo_games_factory),
                contentDescription = "Logotipo de Games Factory",
                modifier = Modifier
                    .size(150.dp) // Ajusta el tamaño (ancho y alto) como más te guste
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit // Hace que mantenga las proporciones perfectas sin deformarse
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- TÍTULO Y SUBTÍTULO ---
            Text(
                text = "Games Factory",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Panel de Administración",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(40.dp))

            // --- CAMPO CORREO ---
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(16.dp), // Bordes bien redonditos
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO CONTRASEÑA ---
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- MENSAJE DE ERROR ---
            // Ocupa un espacio fijo para que los botones no salten de golpe al aparecer el error
            Box(modifier = Modifier.height(24.dp), contentAlignment = Alignment.Center) {
                if (viewModel.loginError.isNotEmpty()) {
                    Text(
                        text = viewModel.loginError,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- BOTÓN ENTRAR ---
            Button(
                onClick = {
                    viewModel.login(user, pass) { rolUsuario ->
                        navController.navigate("${Screens.MainScreen.route}/$rolUsuario")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp), // Botón alto, al estilo de las apps de Google
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "ENTRAR",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}