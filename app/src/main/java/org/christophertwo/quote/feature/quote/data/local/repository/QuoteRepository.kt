package org.christophertwo.quote.feature.quote.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteDao
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteItemDao
import org.christophertwo.quote.feature.quote.data.mapper.QuoteMapper.toDomain
import org.christophertwo.quote.feature.quote.data.mapper.QuoteMapper.toDomainWithItems
import org.christophertwo.quote.feature.quote.data.mapper.QuoteMapper.toEntity
import org.christophertwo.quote.feature.quote.domain.model.Quote
import org.christophertwo.quote.feature.quote.domain.model.QuoteItem
import org.christophertwo.quote.feature.quote.domain.model.QuoteStatus
import org.christophertwo.quote.feature.quote.domain.model.QuoteWithItemsData
import org.christophertwo.quote.feature.quote.domain.repository.IQuoteRepository

/**
 * Implementación del repositorio de Cotizaciones
 */
class QuoteRepository(
    private val quoteDao: QuoteDao,
    private val quoteItemDao: QuoteItemDao
) : IQuoteRepository {

    override fun getAllWithItems(): Flow<List<QuoteWithItemsData>> =
        quoteDao.getAllWithItems().map { it.toDomainWithItems() }

    override fun getByClientId(clientId: Int): Flow<List<QuoteWithItemsData>> =
        quoteDao.getByClientId(clientId).map { it.toDomainWithItems() }

    override suspend fun getWithItemsById(id: Int): QuoteWithItemsData? =
        quoteDao.getWithItemsById(id)?.toDomain()

    override suspend fun getById(id: Int): Quote? =
        quoteDao.getById(id)?.toDomain()

    override fun getByStatus(status: QuoteStatus): Flow<List<QuoteWithItemsData>> =
        quoteDao.getByStatus(status.name).map { it.toDomainWithItems() }

    override suspend fun saveQuote(quote: Quote): Long =
        quoteDao.insert(quote.toEntity())

    override suspend fun updateQuote(quote: Quote) =
        quoteDao.update(quote.toEntity())

    override suspend fun deleteQuote(quote: Quote) =
        quoteDao.delete(quote.toEntity())

    override suspend fun addItem(item: QuoteItem): Long =
        quoteItemDao.insert(item.toEntity())

    override suspend fun updateItem(item: QuoteItem) =
        quoteItemDao.update(item.toEntity())

    override suspend fun deleteItem(item: QuoteItem) =
        quoteItemDao.delete(item.toEntity())

    override suspend fun getCount(): Int =
        quoteDao.getCount()
}
