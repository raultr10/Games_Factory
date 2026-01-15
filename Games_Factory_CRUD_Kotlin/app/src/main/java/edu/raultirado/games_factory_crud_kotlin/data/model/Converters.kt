package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.TypeConverter
import java.util.Date

//Sirve para traducir los enums, los convierte en strings
class Converters {
    @TypeConverter
    fun fromTipoEmp(value: TipoEmpleado) = value.name

    @TypeConverter
    fun toTipoEmp(value: String) = TipoEmpleado.valueOf(value)
    //Pasamos la fecha a Long porque SQLite (la base de datos que usa Room internamente) no tiene un tipo de dato específico para guardar fechas como "Date"
    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(millis: Long?): Date? = millis?.let { Date(it) }

    // --- Conversores para Torneo ---
    @TypeConverter
    fun fromCatTorneo(value: CatTorneo) = value.name

    @TypeConverter
    fun toCatTorneo(value: String) = CatTorneo.valueOf(value)

    @TypeConverter
    fun fromTipoTorneo(value: TipoTorneo) = value.name

    @TypeConverter
    fun toTipoTorneo(value: String): TipoTorneo {
        return if (value == "UnoVsUno") TipoTorneo.UnoVsUno else TipoTorneo.DosVsDos
    }
    // Para Categoría
    @TypeConverter
    fun fromCatV(value: CatV) = value.name

    @TypeConverter
    fun toCatV(value: String) = CatV.valueOf(value)

    // Para Consola
    @TypeConverter
    fun fromTipoC(value: TipoC) = value.name

    @TypeConverter
    fun toTipoC(value: String) = TipoC.valueOf(value)

    // Para Idioma
    @TypeConverter
    fun fromIdio(value: Idio) = value.name

    @TypeConverter
    fun toIdio(value: String) = Idio.valueOf(value)

    @TypeConverter
    fun fromCatNoticia(value: CatNoticia) = value.name

    @TypeConverter
    fun toCatNoticia(value: String) = CatNoticia.valueOf(value)
}