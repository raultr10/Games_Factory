package edu.raultirado.games_factory_crud_kotlin.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.raultirado.games_factory_crud_kotlin.data.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    // Obtener todos los usuarios (usamos Flow para que la lista se actualice sola en la UI)
    @Query("SELECT * FROM Usuario")
    fun getAllUsuarios(): Flow<List<Usuario>>

    // Buscar un usuario por su DNI (Útil para el Login o Perfil)
    @Query("SELECT * FROM Usuario WHERE ID_DNI = :dni")
    suspend fun getUsuarioByDni(dni: String): Usuario?

    //ABORT porque el DNI no puede repetirse
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUsuario(usuario: Usuario)

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)
}