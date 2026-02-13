package org.override.tamplete.di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import org.override.tamplete.feature.auth.presentation.AuthRoot
import org.override.tamplete.feature.home.presentation.HomeRoot
import org.override.tamplete.feature.navigation.routes.RouteGlobal
import org.override.tamplete.feature.navigation.routes.RouteHome
import org.override.tamplete.feature.settings.presentation.SettingsRoot

@OptIn(KoinExperimentalAPI::class)
val screenModule: Module
    get() = module {
        navigation<RouteGlobal.Auth> { AuthRoot(koinViewModel()) }
        navigation<RouteGlobal.Home> { HomeRoot(koinViewModel()) }
        navigation<RouteGlobal.Settings> { SettingsRoot(koinViewModel()) }

        navigation<RouteHome.Pantalla1> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    Text(text = "Pantalla 1", style = MaterialTheme.typography.headlineLarge)
                }
            )
        }
    }