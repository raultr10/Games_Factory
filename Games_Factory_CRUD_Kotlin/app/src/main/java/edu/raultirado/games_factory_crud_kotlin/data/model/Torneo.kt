package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class CatTorneo { Plataforma, Acción, Lucha, Shooter }
enum class TipoTorneo { UnoVsUno, DosVsDos }

@Entity(tableName = "Torneo")
data class Torneo(
    @PrimaryKey
    @ColumnInfo(name = "ID_torneo")
    val idTorneo: String = "",

    val cupo: Int = 0,

    @ColumnInfo(name = "categoria_torneo")
    val categoriaTorneo: CatTorneo,

    @ColumnInfo(name = "tipo_torneo")
    val tipoTorneo: TipoTorneo,

    val nombre: String = "",

    val descripcion: String? = "",

    @ColumnInfo(name = "fecha_torneo")
    val fechaTorneo: Date,

    @ColumnInfo(name = "fecha_creacion")
    val fechaCreacion: Date = Date(),

    val imagen: String = ""
)