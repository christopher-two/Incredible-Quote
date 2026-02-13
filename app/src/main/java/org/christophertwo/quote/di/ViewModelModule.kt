package org.christophertwo.quote.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel
import org.christophertwo.quote.feature.auth.presentation.AuthViewModel
import org.christophertwo.quote.feature.home.presentation.HomeViewModel
import org.christophertwo.quote.feature.settings.presentation.SettingsViewModel
import org.christophertwo.quote.main.MainViewModel

/**
 * MÓDULO DE VIEWMODELS
 *
 * Configura la inyección de dependencias para ViewModels
 * usando Koin Compose ViewModel
 */
val viewModelModule = module {

    /**
     * MainViewModel - ViewModel de la actividad principal
     * viewModel: Crea instancias con lifecycle-aware
     */
    viewModel<MainViewModel>()

    /**
     * AuthViewModel - ViewModel de autenticación
     * Recibe LoginUseCase por DI
     */
    viewModel<AuthViewModel>()

    viewModel<SettingsViewModel>()

    /**
     * HomeViewModel - ViewModel de Home
     * Recibe NavigationController por DI
     */
    viewModel<HomeViewModel>()
}

