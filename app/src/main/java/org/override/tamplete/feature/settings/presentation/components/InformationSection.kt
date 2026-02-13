package org.override.tamplete.feature.settings.presentation.components

import androidx.compose.foundation.lazy.LazyListScope
import org.override.tamplete.feature.settings.presentation.SettingsAction

/**
 * Sección de información y enlaces
 */
fun LazyListScope.informationSection(
    onAction: (SettingsAction) -> Unit
) {
    item {
        SettingsSectionHeader(title = "INFORMATION")
    }

    item {
        SettingsLinkItem(
            label = "About",
            description = "Version 1.0.0",
            onClick = { onAction(SettingsAction.OnAboutClick) }
        )
    }

    item {
        SettingsLinkItem(
            label = "Help & Support",
            onClick = { onAction(SettingsAction.OnHelpClick) }
        )
    }

    item {
        SettingsLinkItem(
            label = "Privacy Policy",
            onClick = { onAction(SettingsAction.OnPrivacyPolicyClick) }
        )
    }

    item {
        SettingsLinkItem(
            label = "Terms of Service",
            onClick = { onAction(SettingsAction.OnTermsClick) }
        )
    }
}

