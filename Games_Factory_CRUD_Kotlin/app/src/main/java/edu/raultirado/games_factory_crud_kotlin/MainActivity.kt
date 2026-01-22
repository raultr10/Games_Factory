package edu.raultirado.games_factory_crud_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.raultirado.games_factory_crud_kotlin.ui.AppNavigation
import edu.raultirado.games_factory_crud_kotlin.ui.theme.Games_Factory_CRUD_KotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Games_Factory_CRUD_KotlinTheme {
                AppNavigation()
            }
        }
    }
}