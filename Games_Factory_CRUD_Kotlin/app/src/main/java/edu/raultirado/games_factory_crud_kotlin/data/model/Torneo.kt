package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

enum class CatTorneo { Plataforma, Acción, Lucha, Shooter }
enum class TipoTorneo { UnoVsUno, DosVsDos }

@Entity(tableName = "Torneo")
data class Torneo(
    @PrimaryKey
    val idTorneo: String,
    val cupo: Int,
    val categoriaTorneo: CatTorneo,
    val tipoTorneo: TipoTorneo,
    val nombre: String,
    val descripcion: String?,
    val fechaTorneo: Date,
    val fechaCreacion: Date = Date(),
    val imagen: String
)