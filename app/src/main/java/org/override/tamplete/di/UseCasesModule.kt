package org.override.tamplete.di

import org.koin.dsl.module
import org.koin.plugin.module.dsl.factory
import org.override.tamplete.feature.auth.domain.usecase.CheckSessionUseCase
import org.override.tamplete.feature.auth.domain.usecase.LoginUseCase
import org.override.tamplete.feature.settings.domain.usecases.GetThemePreferencesUseCase
import org.override.tamplete.feature.settings.domain.usecases.ResetThemePreferencesUseCase
import org.override.tamplete.feature.settings.domain.usecases.SettingsUseCases
import org.override.tamplete.feature.settings.domain.usecases.UpdateContrastLevelUseCase
import org.override.tamplete.feature.settings.domain.usecases.UpdateDarkModeUseCase
import org.override.tamplete.feature.settings.domain.usecases.UpdateDynamicColorsUseCase
import org.override.tamplete.feature.settings.domain.usecases.UpdatePaletteStyleUseCase
import org.override.tamplete.feature.settings.domain.usecases.UpdateSeedColorUseCase

/**
 * MÓDULO DE CASOS DE USO
 *
 * Configura la inyección de dependencias para los casos de uso
 * de la capa de dominio
 */
val useCasesModule = module {

    // Auth Use Cases
    factory<LoginUseCase>()
    factory<CheckSessionUseCase>()

    // Theme Use Cases individuales
    factory<GetThemePreferencesUseCase>()
    factory<UpdateDarkModeUseCase>()
    factory<UpdateDynamicColorsUseCase>()
    factory<UpdateSeedColorUseCase>()
    factory<UpdatePaletteStyleUseCase>()
    factory<UpdateContrastLevelUseCase>()
    factory<ResetThemePreferencesUseCase>()

    // Agrupador de casos de uso de Settings
    factory<SettingsUseCases>()
}

