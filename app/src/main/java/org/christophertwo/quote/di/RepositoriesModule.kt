package org.christophertwo.quote.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import org.christophertwo.quote.feature.products.data.local.dao.ProductDao
import org.christophertwo.quote.feature.products.data.local.repository.ProductRepository
import org.christophertwo.quote.feature.products.domain.repository.IProductRepository
import org.christophertwo.quote.feature.clients.data.local.dao.ClientDao
import org.christophertwo.quote.feature.clients.data.local.repository.ClientRepository
import org.christophertwo.quote.feature.clients.domain.repository.IClientRepository
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteDao
import org.christophertwo.quote.feature.quote.data.local.dao.QuoteItemDao
import org.christophertwo.quote.feature.quote.data.local.repository.QuoteRepository
import org.christophertwo.quote.feature.quote.domain.repository.IQuoteRepository

/**
 * MÓDULO DE KOIN PARA LOS REPOSITORIOS
 *
 * Configura la inyección de dependencias para los repositorios
 * Las interfaces se inyectan como single (singleton) reutilizando la implementación
 */
val repositoriesModule = module {

    // Repositorio de Productos
    single<IProductRepository> {
        ProductRepository(
            productDao = get<ProductDao>()
        )
    }

    // Repositorio de Clientes
    single<IClientRepository> {
        ClientRepository(
            clientDao = get<ClientDao>()
        )
    }

    // Repositorio de Cotizaciones
    single<IQuoteRepository> {
        QuoteRepository(
            quoteDao = get<QuoteDao>(),
            quoteItemDao = get<QuoteItemDao>()
        )
    }
}
