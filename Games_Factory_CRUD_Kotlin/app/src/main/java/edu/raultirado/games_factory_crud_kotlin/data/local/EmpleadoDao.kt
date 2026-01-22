package edu.raultirado.games_factory_crud_kotlin.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.raultirado.games_factory_crud_kotlin.data.model.CategoriaEmpleado
import edu.raultirado.games_factory_crud_kotlin.data.model.Empleado
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM Empleado")
    fun getAllEmpleados(): Flow<List<Empleado>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEmpleado(empleado: Empleado)

    //Sirve para asignarle categoria al empleado, o si ya tiene la actualiza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoria(categoria: CategoriaEmpleado)

    @Delete
    suspend fun deleteEmpleado(empleado: Empleado)
}