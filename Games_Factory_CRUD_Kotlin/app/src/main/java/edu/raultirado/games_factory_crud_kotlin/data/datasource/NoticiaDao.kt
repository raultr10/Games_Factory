package edu.raultirado.games_factory_crud_kotlin.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.raultirado.games_factory_crud_kotlin.data.model.Noticia
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticiaDao {
    @Query("SELECT * FROM Noticia ORDER BY fecha_creacion DESC")
    fun getAllNoticias(): Flow<List<Noticia>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoticia(noticia: Noticia)

    @Delete
    suspend fun deleteNoticia(noticia: Noticia)
}