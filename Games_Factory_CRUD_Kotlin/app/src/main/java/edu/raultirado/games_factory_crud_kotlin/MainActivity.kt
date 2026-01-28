package edu.raultirado.games_factory_crud_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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