package org.christophertwo.quote.feature.quote.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.model.QuoteItem
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus
import org.christophertwo.quote.feature.quote.domain.model.QuoteWithItemsData
import org.christophertwo.quote.feature.quote.domain.repository.IQuoteRepository

/**
 * Caso de uso: Obtener todas las cotizaciones con items
 */
class GetAllQuotesUseCase(
    private val repository: IQuoteRepository
) {
    operator fun invoke(): Flow<List<QuoteWithItemsData>> = repository.getAllWithItems()
}

/**
 * Caso de uso: Obtener cotizaciones de un cliente
 */
class GetQuotesByClientUseCase(
    private val repository: IQuoteRepository
) {
    operator fun invoke(clientId: String): Flow<List<QuoteWithItemsData>> =
        repository.getByClientId(clientId)
}

/**
 * Caso de uso: Obtener cotización con items por ID
 */
class GetQuoteWithItemsUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(id: String): QuoteWithItemsData? =
        repository.getWithItemsById(id)
}

/**
 * Caso de uso: Crear cotización
 */
class CreateQuoteUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(quote: Quote): Long = repository.saveQuote(quote)
}

/**
 * Caso de uso: Actualizar cotización
 */
class UpdateQuoteUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(quote: Quote) = repository.updateQuote(quote)
}

/**
 * Caso de uso: Eliminar cotización
 */
class DeleteQuoteUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(quote: Quote) = repository.deleteQuote(quote)
}

/**
 * Caso de uso: Agregar item a cotización
 */
class AddItemToQuoteUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(item: QuoteItem): Long = repository.addItem(item)
}

/**
 * Caso de uso: Eliminar item de cotización
 */
class RemoveItemFromQuoteUseCase(
    private val repository: IQuoteRepository
) {
    suspend operator fun invoke(item: QuoteItem) = repository.deleteItem(item)
}

/**
 * Caso de uso: Obtener cotizaciones por estado
 */
class GetQuotesByStatusUseCase(
    private val repository: IQuoteRepository
) {
    operator fun invoke(status: QuoteStatus): Flow<List<QuoteWithItemsData>> =
        repository.getByStatus(status)
}

/**
 * Agregador de casos de uso de Quote
 */
data class QuoteUseCases(
    val getAllQuotes: GetAllQuotesUseCase,
    val getQuotesByClient: GetQuotesByClientUseCase,
    val getQuoteWithItems: GetQuoteWithItemsUseCase,
    val createQuote: CreateQuoteUseCase,
    val updateQuote: UpdateQuoteUseCase,
    val deleteQuote: DeleteQuoteUseCase,
    val addItemToQuote: AddItemToQuoteUseCase,
    val removeItemFromQuote: RemoveItemFromQuoteUseCase,
    val getQuotesByStatus: GetQuotesByStatusUseCase
)
