package edu.raultirado.games_factory_crud_kotlin.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.raultirado.games_factory_crud_kotlin.data.model.*

@Database(
    entities = [
        Usuario::class,
        Empleado::class,
        CategoriaEmpleado::class,
        Producto::class,
        Videojuego::class,
        Torneo::class,
        Inscripcion::class,
        Noticia::class
    ],
    version = 1,
    exportSchema = true // Genera el JSON con la estructura
)
//Registramos los conversores para fechas y enums
@TypeConverters(Converters::class)
abstract class GamesFactoryDatabase : RoomDatabase() {

    // 3. Declaramos los DAOs
    abstract fun usuarioDao(): UsuarioDao
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun productoDao(): ProductoDao
    abstract fun torneoDao(): TorneoDao
    abstract fun noticiaDao(): NoticiaDao
    abstract fun inscripcionDao(): InscripcionDao

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