package edu.raultirado.games_factory_crud_kotlin.data.local

import androidx.room.*
import edu.raultirado.games_factory_crud_kotlin.data.model.Producto
import edu.raultirado.games_factory_crud_kotlin.data.model.Videojuego
import edu.raultirado.games_factory_crud_kotlin.data.model.ProductoVideojuego
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducto(producto: Producto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideojuego(videojuego: Videojuego)

    @Transaction
    @Query("SELECT * FROM Producto")
    fun getProductosConVideojuegos(): Flow<List<ProductoVideojuego>>

    @Delete
    suspend fun deleteProducto(producto: Producto)
}