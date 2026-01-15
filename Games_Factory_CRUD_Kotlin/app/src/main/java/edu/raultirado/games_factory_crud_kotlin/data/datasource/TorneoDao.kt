package edu.raultirado.games_factory_crud_kotlin.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.raultirado.games_factory_crud_kotlin.data.model.Torneo
import kotlinx.coroutines.flow.Flow

@Dao
interface TorneoDao {
    @Query("SELECT * FROM Torneo ORDER BY fecha_torneo ASC")
    fun getAllTorneos(): Flow<List<Torneo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTorneo(torneo: Torneo): Long

    @Delete
    suspend fun deleteTorneo(torneo: Torneo): Int
}