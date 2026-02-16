package org.christophertwo.quote.feature.quote.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.model.QuoteItem
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus
import org.christophertwo.quote.feature.quote.domain.model.QuoteWithItemsData

/**
 * Interfaz de Repositorio de Cotizaciones
 */
interface IQuoteRepository {

    /**
     * Obtener todas las cotizaciones con sus items
     */
    fun getAllWithItems(): Flow<List<QuoteWithItemsData>>

    /**
     * Obtener cotizaciones por cliente
     */
    fun getByClientId(clientId: Int): Flow<List<QuoteWithItemsData>>

    /**
     * Obtener cotización con items por ID
     */
    suspend fun getWithItemsById(id: Int): QuoteWithItemsData?

    /**
     * Obtener cotización simple por ID
     */
    suspend fun getById(id: Int): Quote?

    /**
     * Obtener cotizaciones por estado
     */
    fun getByStatus(status: QuoteStatus): Flow<List<QuoteWithItemsData>>

    /**
     * Guardar cotización
     */
    suspend fun saveQuote(quote: Quote): Long

    /**
     * Actualizar cotización
     */
    suspend fun updateQuote(quote: Quote)

    /**
     * Eliminar cotización
     */
    suspend fun deleteQuote(quote: Quote)

    /**
     * Agregar item a cotización
     */
    suspend fun addItem(item: QuoteItem): Long

    /**
     * Actualizar item
     */
    suspend fun updateItem(item: QuoteItem)

    /**
     * Eliminar item
     */
    suspend fun deleteItem(item: QuoteItem)

    /**
     * Obtener cantidad de cotizaciones
     */
    suspend fun getCount(): Int
}
