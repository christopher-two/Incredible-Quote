package org.christophertwo.quote.feature.quote.domain.repository

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.quote.domain.model.Quote

/**
 * QUOTE REPOSITORY INTERFACE
 *
 * Define el contrato para la gestión de persistencia de Quote
 * Esta interfaz pertenece al dominio y no depende de implementaciones concretas
 * Sigue el principio de inversión de dependencias (SOLID)
 */
interface QuoteRepository {

    /**
     * Flow que emite el Quote actual
     * Se actualiza automáticamente cuando cambia
     */
    val quoteFlow: Flow<Quote>

    /**
     * Guarda el Quote
     *
     * @param quote Quote a guardar
     */
    suspend fun saveQuote(quote: Quote)

    /**
     * Obtiene el Quote actual
     *
     * @return Quote actual o Quote.empty() si no existe
     */
    suspend fun getQuote(): Quote

    /**
     * Elimina el Quote guardado
     */
    suspend fun clearQuote()
}