package org.christophertwo.quote.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel
import org.christophertwo.quote.feature.auth.presentation.AuthViewModel
import org.christophertwo.quote.feature.home.presentation.HomeViewModel
import org.christophertwo.quote.feature.products.presentation.ProductsViewModel
import org.christophertwo.quote.feature.quote.presentation.QuoteViewModel
import org.christophertwo.quote.feature.settings.presentation.SettingsViewModel
import org.christophertwo.quote.main.MainViewModel

/**
 * MÓDULO DE VIEWMODELS
 *
 * Configura la inyección de dependencias para ViewModels
 * usando Koin Compose ViewModel
 */
val viewModelModule = module {
    viewModel<MainViewModel>()
    viewModel<AuthViewModel>()
    viewModel<SettingsViewModel>()
    viewModel<HomeViewModel>()
    viewModel<QuoteViewModel>()
    viewModel<ProductsViewModel>()
}

