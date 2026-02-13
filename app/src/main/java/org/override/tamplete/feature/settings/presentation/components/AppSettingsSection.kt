package org.override.tamplete.feature.settings.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.override.tamplete.feature.settings.presentation.SettingsAction
import org.override.tamplete.feature.settings.presentation.SettingsState

/**
 * Sección de configuraciones de la aplicación
 */
fun LazyListScope.appSettingsSection(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
) {
    item {
        SettingsSectionHeader(title = "APP SETTINGS")
    }

    item {
        SettingsSwitchItem(
            label = "Notifications",
            description = "Receive push notifications",
            checked = state.notificationsEnabled,
            onCheckedChange = { onAction(SettingsAction.OnNotificationsToggle(it)) }
        )
    }

    item {
        SettingsSwitchItem(
            label = "Auto Sync",
            description = "Automatically sync data",
            checked = state.autoSyncEnabled,
            onCheckedChange = { onAction(SettingsAction.OnAutoSyncToggle(it)) }
        )
    }

    item {
        SettingsSwitchItem(
            label = "Data Collection",
            description = "Help improve the app",
            checked = state.dataCollectionEnabled,
            onCheckedChange = { onAction(SettingsAction.OnDataCollectionToggle(it)) }
        )
    }

    item {
        SettingsActionItem(
            label = "Clear Cache",
            description = "Current size: ${state.cacheSize}",
            actionLabel = "Clear",
            onClick = { onAction(SettingsAction.OnClearCache) }
        )
    }

    item {
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

