package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "videojuego")
data class Videojuego(
    @PrimaryKey val idProductoJuego: Long = 0,
    val categoria_videojuego: CatV,
    val tipo_consola: TipoC,
    val idioma: Idio,
    val compania: String
)
{
    enum class CatV { Plataforma, Accion, Terror, Aventura }
    enum class TipoC { Nintendo, Xbox, Playstation, PC }
    enum class Idio { IN, SP, JP }
}
class Converters {
    @TypeConverter
    fun fromCatV(value: Videojuego.CatV) = value.name

    @TypeConverter
    fun toCatV(value: String) = Videojuego.CatV.valueOf(value)

    @TypeConverter
    fun fromTipoC(value: Videojuego.TipoC) = value.name

    @TypeConverter
    fun toTipoC(value: String) = Videojuego.TipoC.valueOf(value)

    @TypeConverter
    fun fromIdio(value: Videojuego.Idio) = value.name

    @TypeConverter
    fun toIdio(value: String) = Videojuego.Idio.valueOf(value)
}