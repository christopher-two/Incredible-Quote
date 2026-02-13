package org.override.tamplete.feature.settings.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.materialkolor.PaletteStyle
import org.override.tamplete.feature.settings.presentation.SettingsAction
import org.override.tamplete.feature.settings.presentation.SettingsState

/**
 * Sección de configuraciones del tema
 */
fun LazyListScope.themeSettingsSection(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    item {
        SettingsSectionHeader(title = "THEME SETTINGS")
    }

    item {
        SettingsSwitchItem(
            label = "Dark Mode",
            description = if (!state.useDynamicColors) {
                "Enable Dynamic Colors to use Dark Mode"
            } else {
                "Use dark theme"
            },
            checked = state.isDarkMode,
            onCheckedChange = { onAction(SettingsAction.OnDarkModeToggle(it)) },
            enabled = state.useDynamicColors
        )
    }

    item {
        SettingsSwitchItem(
            label = "Dynamic Colors",
            description = "Use colors from wallpaper (Android 12+)",
            checked = state.useDynamicColors,
            onCheckedChange = { onAction(SettingsAction.OnDynamicColorsToggle(it)) }
        )
    }

    if (!state.useDynamicColors) {
        item {
            SettingsColorPickerItem(
                label = "Seed Color",
                selectedColor = state.seedColor,
                onColorClick = { onAction(SettingsAction.OnColorPickerToggle) }
            )
        }

        item {
            SettingsDropdownItem(
                label = "Palette Style",
                selectedOption = state.paletteStyle,
                options = listOf(
                    PaletteStyle.TonalSpot,
                    PaletteStyle.Neutral,
                    PaletteStyle.Vibrant,
                    PaletteStyle.Expressive,
                    PaletteStyle.Rainbow,
                    PaletteStyle.FruitSalad,
                    PaletteStyle.Monochrome,
                    PaletteStyle.Fidelity,
                    PaletteStyle.Content
                ),
                onOptionSelected = { onAction(SettingsAction.OnPaletteStyleChange(it)) },
                optionLabel = { it.name }
            )
        }
    }

    item {
        SettingsSliderItem(
            label = "Contrast Level",
            value = state.contrastLevel,
            onValueChange = { onAction(SettingsAction.OnContrastLevelChange(it)) },
            valueRange = -1f..1f,
            steps = 19,
            valueFormatter = { String.format("%.1f", it) }
        )
    }

    item {
        SettingsActionItem(
            label = "Reset Theme",
            description = "Restore default theme settings",
            actionLabel = "Reset",
            onClick = { onAction(SettingsAction.OnResetTheme) }
        )
    }

    item {
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

