package org.override.tamplete.feature.settings.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.tamplete.core.ui.theme.AppTheme
import org.override.tamplete.feature.settings.presentation.components.ColorPickerDialog
import org.override.tamplete.feature.settings.presentation.components.SettingsTopBar
import org.override.tamplete.feature.settings.presentation.components.appSettingsSection
import org.override.tamplete.feature.settings.presentation.components.informationSection
import org.override.tamplete.feature.settings.presentation.components.themeSettingsSection
import org.override.tamplete.feature.settings.presentation.screens.AboutScreen
import org.override.tamplete.feature.settings.presentation.screens.HelpScreen
import org.override.tamplete.feature.settings.presentation.screens.PrivacyPolicyScreen
import org.override.tamplete.feature.settings.presentation.screens.TermsScreen

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        onAction(SettingsAction.OnBackClick)
    }

    // Manejar mensajes del Snackbar
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onAction(SettingsAction.OnSnackbarDismissed)
        }
    }

    Scaffold(
        topBar = {
            SettingsTopBar(
                title = when (state.currentScreen) {
                    SettingsDestination.MAIN -> "Settings"
                    SettingsDestination.ABOUT -> "About"
                    SettingsDestination.HELP -> "Help & Support"
                    SettingsDestination.PRIVACY_POLICY -> "Privacy Policy"
                    SettingsDestination.TERMS -> "Terms of Service"
                },
                onBackClick = { onAction(SettingsAction.OnBackClick) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        // Navegación simple con when
        when (state.currentScreen) {
            SettingsDestination.MAIN -> {
                MainSettingsContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
            SettingsDestination.ABOUT -> {
                AboutScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            SettingsDestination.HELP -> {
                HelpScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            SettingsDestination.PRIVACY_POLICY -> {
                PrivacyPolicyScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
            SettingsDestination.TERMS -> {
                TermsScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }

        // Color Picker Dialog
        if (state.showColorPicker) {
            ColorPickerDialog(
                currentColor = state.seedColor,
                onColorSelected = { color ->
                    onAction(SettingsAction.OnSeedColorChange(color))
                },
                onDismiss = { onAction(SettingsAction.OnColorPickerToggle) }
            )
        }
    }
}

@Composable
private fun MainSettingsContent(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        // Theme Settings Section
        themeSettingsSection(
            state = state,
            onAction = onAction
        )

        // App Settings Section
        appSettingsSection(
            state = state,
            onAction = onAction
        )

        // Information Section
        informationSection(
            onAction = onAction
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        SettingsScreen(
            state = SettingsState(),
            onAction = {}
        )
    }
}

