package org.christophertwo.quote.di

import org.christophertwo.quote.feature.auth.presentation.AuthRoot
import org.christophertwo.quote.feature.home.presentation.HomeRoot
import org.christophertwo.quote.feature.navigation.routes.RouteGlobal
import org.christophertwo.quote.feature.navigation.routes.RouteHome
import org.christophertwo.quote.feature.products.presentation.ProductsRoot
import org.christophertwo.quote.feature.quote.presentation.QuoteRoot
import org.christophertwo.quote.feature.settings.presentation.SettingsRoot
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation

@OptIn(KoinExperimentalAPI::class)
val screenModule: Module
    get() = module {
        navigation<RouteGlobal.Auth> { AuthRoot(koinViewModel()) }
        navigation<RouteGlobal.Home> { HomeRoot(koinViewModel()) }
        navigation<RouteGlobal.Settings> { SettingsRoot(koinViewModel()) }

        navigation<RouteHome.Quote> { QuoteRoot(koinViewModel()) }
        navigation<RouteHome.Products> { ProductsRoot(koinViewModel()) }
    }