package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

enum class CatV { Plataforma, Accion, Terror, Aventura }
enum class TipoC { Nintendo, Xbox, Playstation, PC }
enum class Idio { IN, SP, JP }
@Entity(
    tableName = "videojuego",
    foreignKeys = [
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["ID_producto"],
            childColumns = ["ID_producto"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Videojuego(
    @PrimaryKey
    @ColumnInfo(name = "ID_producto")
    val idProducto: String = "",

    @ColumnInfo(name = "categoria_videojuego")
    val categoriavideojuego: CatV,

    @ColumnInfo(name = "tipo_consola")
    val tipoconsola: TipoC,

    val idioma: Idio,
    val compania: String = ""
)