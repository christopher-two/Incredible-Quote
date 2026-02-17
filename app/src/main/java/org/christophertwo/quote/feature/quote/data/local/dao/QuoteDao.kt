package org.christophertwo.quote.feature.quote.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteEntity
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteWithItems

/**
 * Data Access Object para Cotizaciones
 */
@Dao
interface QuoteDao {

    /**
     * Obtener todas las cotizaciones con sus items
     */
    @Transaction
    @Query("SELECT * FROM quotes ORDER BY createdAt DESC")
    fun getAllWithItems(): Flow<List<QuoteWithItems>>

    /**
     * Obtener cotizaciones por cliente
     */
    @Transaction
    @Query("SELECT * FROM quotes WHERE clientId = :clientId ORDER BY createdAt DESC")
    fun getByClientId(clientId: String): Flow<List<QuoteWithItems>>

    /**
     * Obtener cotización con items por ID
     */
    @Transaction
    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getWithItemsById(id: String): QuoteWithItems?

    /**
     * Obtener cotización simple por ID
     */
    @Query("SELECT * FROM quotes WHERE id = :id")
    suspend fun getById(id: String): QuoteEntity?

    /**
     * Obtener cotizaciones por estado
     */
    @Transaction
    @Query("SELECT * FROM quotes WHERE status = :status ORDER BY createdAt DESC")
    fun getByStatus(status: String): Flow<List<QuoteWithItems>>

    /**
     * Insertar cotización
     */
    @Insert
    suspend fun insert(quote: QuoteEntity): Long

    /**
     * Actualizar cotización
     */
    @Update
    suspend fun update(quote: QuoteEntity)

    /**
     * Eliminar cotización
     */
    @Delete
    suspend fun delete(quote: QuoteEntity)

    /**
     * Obtener cantidad de cotizaciones
     */
    @Query("SELECT COUNT(*) FROM quotes")
    suspend fun getCount(): Int
}
