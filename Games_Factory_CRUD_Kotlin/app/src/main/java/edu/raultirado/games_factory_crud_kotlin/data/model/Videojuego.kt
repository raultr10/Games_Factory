package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CatV { Plataforma, Accion, Terror, Aventura }
enum class TipoC { Nintendo, Xbox, Playstation, PC }
enum class Idio { IN, SP, JP }
@Entity(tableName = "videojuego")
data class Videojuego(
    @PrimaryKey val idProductoJuego: Long = 0,
    @ColumnInfo(name = "categoria_videojuego")
    val categoriavideojuego: CatV,
    @ColumnInfo(name = "tipo_consola")
    val tipoconsola: TipoC,
    val idioma: Idio,
    val compania: String
)