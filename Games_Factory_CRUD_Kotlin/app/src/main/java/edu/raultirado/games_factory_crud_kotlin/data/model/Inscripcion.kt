package edu.raultirado.games_factory_crud_kotlin.data.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Inscripcion",
    primaryKeys = ["id_torneo", "id_DNI"],
    foreignKeys = [
        ForeignKey(
            entity = Torneo::class,
            parentColumns = ["ID_torneo"],
            childColumns = ["id_torneo"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["ID_DNI"],
            childColumns = ["id_DNI"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Inscripcion(
    val id_torneo: String = "",
    val id_DNI: String = ""
)