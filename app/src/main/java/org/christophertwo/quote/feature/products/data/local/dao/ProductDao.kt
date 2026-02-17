package org.christophertwo.quote.feature.products.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.products.data.local.entity.ProductEntity

/**
 * Data Access Object para Productos
 * Define las operaciones CRUD en la tabla de productos
 */
@Dao
interface ProductDao {

    /**
     * Obtener todos los productos activos como Flow
     */
    @Query("SELECT * FROM products WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActive(): Flow<List<ProductEntity>>

    /**
     * Obtener todos los productos incluyendo inactivos
     */
    @Query("SELECT * FROM products ORDER BY name ASC")
    fun getAll(): Flow<List<ProductEntity>>

    /**
     * Obtener producto por ID
     */
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: String): ProductEntity?

    /**
     * Buscar productos por nombre
     */
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' AND isActive = 1")
    fun searchByName(query: String): Flow<List<ProductEntity>>

    /**
     * Insertar producto
     */
    @Insert
    suspend fun insert(product: ProductEntity): Long

    /**
     * Actualizar producto
     */
    @Update
    suspend fun update(product: ProductEntity)

    /**
     * Eliminar producto
     */
    @Delete
    suspend fun delete(product: ProductEntity)

    /**
     * Obtener cantidad de productos
     */
    @Query("SELECT COUNT(*) FROM products")
    suspend fun getCount(): Int
}
