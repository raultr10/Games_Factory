package edu.raultirado.games_factory_crud_kotlin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.raultirado.games_factory_crud_kotlin.data.model.*

@Database(
    entities = [
        Usuario::class
    ],
    version = 1,
    exportSchema = true // Genera el JSON con la estructura
)


abstract class GamesFactoryDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    companion object {
        @Volatile
        private var INSTANCE: GamesFactoryDatabase? = null

        fun getInstance(context: Context): GamesFactoryDatabase {
            // Si la instancia ya existe, la devuelve; si no, la crea de forma segura (synchronized)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GamesFactoryDatabase::class.java,
                    "games_factory.db" // Nombre del archivo de la base de datos
                )
                    // Si cambias la versión o las clases, borra y recrea para evitar crashes en desarrollo
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}