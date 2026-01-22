package edu.raultirado.games_factory_crud_kotlin.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import edu.raultirado.games_factory_crud_kotlin.data.model.Inscripcion

@Dao
interface InscripcionDao {
    //ABORT para que un usuario no se inscriba dos veces en el mismo torneo
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertInscripcion(inscripcion: Inscripcion)

    @Delete
    suspend fun deleteInscripcion(inscripcion: Inscripcion)
}