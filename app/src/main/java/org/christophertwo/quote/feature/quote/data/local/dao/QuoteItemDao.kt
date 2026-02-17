package org.christophertwo.quote.feature.quote.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.quote.data.local.entity.QuoteItemEntity

/**
 * Data Access Object para Items de Cotización
 */
@Dao
interface QuoteItemDao {

    /**
     * Obtener todos los items de una cotización
     */
    @Query("SELECT * FROM quote_items WHERE quoteId = :quoteId ORDER BY id ASC")
    fun getByQuoteId(quoteId: Int): Flow<List<QuoteItemEntity>>

    /**
     * Obtener item por ID
     */
    @Query("SELECT * FROM quote_items WHERE id = :id")
    suspend fun getById(id: String): QuoteItemEntity?

    /**
     * Insertar item
     */
    @Insert
    suspend fun insert(item: QuoteItemEntity): Long

    /**
     * Actualizar item
     */
    @Update
    suspend fun update(item: QuoteItemEntity)

    /**
     * Eliminar item
     */
    @Delete
    suspend fun delete(item: QuoteItemEntity)

    /**
     * Eliminar todos los items de una cotización
     */
    @Query("DELETE FROM quote_items WHERE quoteId = :quoteId")
    suspend fun deleteByQuoteId(quoteId: Int)

    /**
     * Obtener cantidad de items en una cotización
     */
    @Query("SELECT COUNT(*) FROM quote_items WHERE quoteId = :quoteId")
    suspend fun getCountByQuoteId(quoteId: Int): Int
}
