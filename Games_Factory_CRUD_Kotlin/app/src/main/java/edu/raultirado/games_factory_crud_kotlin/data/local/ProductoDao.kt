package edu.raultirado.games_factory_crud_kotlin.data.local

import androidx.room.*
import edu.raultirado.games_factory_crud_kotlin.data.model.Producto
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    // Solo mantenemos operaciones con 'Producto', que sigue siendo una Entity válida.
    // Borramos todo lo referente a 'Videojuego' y 'ProductoVideojuego' porque
    // ahora esos datos los traemos del servidor, no de Room.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: Producto)

    @Query("SELECT * FROM Producto")
    suspend fun getAllProductos(): List<Producto>

    // Si tenías métodos como 'getProductosConVideojuegos' o 'insertVideojuego',
    // han sido eliminados para evitar el error.
}