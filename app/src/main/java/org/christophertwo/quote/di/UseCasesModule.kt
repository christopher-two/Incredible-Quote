package org.christophertwo.quote.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory
import org.christophertwo.quote.feature.auth.domain.usecase.CheckSessionUseCase
import org.christophertwo.quote.feature.auth.domain.usecase.LoginUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.GenerateQuoteMessageUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.SearchProductsUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.GetThemePreferencesUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.ResetThemePreferencesUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.SettingsUseCases
import org.christophertwo.quote.feature.settings.domain.usecases.UpdateContrastLevelUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.UpdateDarkModeUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.UpdateDynamicColorsUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.UpdatePaletteStyleUseCase
import org.christophertwo.quote.feature.settings.domain.usecases.UpdateSeedColorUseCase
import org.christophertwo.quote.feature.products.domain.usecase.ProductUseCases
import org.christophertwo.quote.feature.products.domain.usecase.GetAllProductsUseCase
import org.christophertwo.quote.feature.products.domain.usecase.SaveProductUseCase
import org.christophertwo.quote.feature.products.domain.usecase.GetProductByIdUseCase
import org.christophertwo.quote.feature.products.domain.usecase.DeleteProductUseCase
import org.christophertwo.quote.feature.products.domain.usecase.UpdateProductUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.ClientUseCases
import org.christophertwo.quote.feature.clients.domain.usecase.GetAllClientsUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.SaveClientUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.GetClientByIdUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.DeleteClientUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.UpdateClientUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.SearchClientsUseCase
import org.christophertwo.quote.feature.clients.domain.usecase.GetClientsByTypeUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.QuoteUseCases
import org.christophertwo.quote.feature.quote.domain.usecase.GetAllQuotesUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.GetQuotesByClientUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.GetQuoteWithItemsUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.CreateQuoteUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.UpdateQuoteUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.DeleteQuoteUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.AddItemToQuoteUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.RemoveItemFromQuoteUseCase
import org.christophertwo.quote.feature.quote.domain.usecase.GetQuotesByStatusUseCase
import org.koin.plugin.module.dsl.single

/**
 * MÓDULO DE CASOS DE USO
 *
 * Configura la inyección de dependencias para los casos de uso
 * de la capa de dominio
 *
 * Nota: Usamos factory para los casos de uso individuales y single para los agregadores
 */
val useCasesModule = module {

    // ==================== AUTH USE CASES ====================
    factory<LoginUseCase>()
    factory<CheckSessionUseCase>()

    // ==================== EXISTING QUOTE USE CASES ====================
    factory<SearchProductsUseCase>()
    factory<GenerateQuoteMessageUseCase>()

    // ==================== THEME USE CASES ====================
    factory<GetThemePreferencesUseCase>()
    factory<UpdateDarkModeUseCase>()
    factory<UpdateDynamicColorsUseCase>()
    factory<UpdateSeedColorUseCase>()
    factory<UpdatePaletteStyleUseCase>()
    factory<UpdateContrastLevelUseCase>()
    factory<ResetThemePreferencesUseCase>()

    // Agrupador de casos de uso de Settings
    factory<SettingsUseCases>()

    // ==================== PRODUCT USE CASES ====================
    factory<GetAllProductsUseCase>()
    factory<SaveProductUseCase>()
    factory<GetProductByIdUseCase>()
    factory<DeleteProductUseCase>()
    factory<UpdateProductUseCase>()

    // Agregador de casos de uso de Productos
    single<ProductUseCases>()

    // ==================== CLIENT USE CASES ====================
    factory<GetAllClientsUseCase>()
    factory<SaveClientUseCase>()
    factory<GetClientByIdUseCase>()
    factory<DeleteClientUseCase>()
    factory<UpdateClientUseCase>()
    factory<SearchClientsUseCase>()
    factory<GetClientsByTypeUseCase>()

    // Agregador de casos de uso de Clientes
    single<ClientUseCases>()

    // ==================== QUOTE USE CASES ====================
    factory<GetAllQuotesUseCase>()
    factory<GetQuotesByClientUseCase>()
    factory<GetQuoteWithItemsUseCase>()
    factory<CreateQuoteUseCase>()
    factory<UpdateQuoteUseCase>()
    factory<DeleteQuoteUseCase>()
    factory<AddItemToQuoteUseCase>()
    factory<RemoveItemFromQuoteUseCase>()
    factory<GetQuotesByStatusUseCase>()

    // Agregador de casos de uso de Cotizaciones
    single<QuoteUseCases>()
}


