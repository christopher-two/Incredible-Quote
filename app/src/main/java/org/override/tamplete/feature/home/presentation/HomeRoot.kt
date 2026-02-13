package org.override.tamplete.feature.home.presentation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.tamplete.core.ui.theme.AppTheme
import org.override.tamplete.feature.home.presentation.components.ExitAppDialog
import org.override.tamplete.feature.home.presentation.components.HomeBottomBar
import org.override.tamplete.feature.home.presentation.components.HomeFab
import org.override.tamplete.feature.home.presentation.components.HomeTopBar
import org.override.tamplete.feature.navigation.routes.AppTab
import org.override.tamplete.feature.navigation.wrappers.HomeNavigationWrapper

@Composable
fun HomeRoot(
    viewModel: HomeViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Manejar mensajes del Snackbar
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onAction(HomeAction.OnSnackbarDismissed)
        }
    }

    BackHandler(
        enabled = state.selectedTab == AppTab.Screen1,
        onBack = { onAction(HomeAction.OnBackPressed) }
    )

    Scaffold(
        topBar = {
            HomeTopBar(
                title = state.topBarTitle,
                onSettingsClick = {
                    onAction(HomeAction.OnSettingsClick)
                },
            )
        },
        bottomBar = {
            HomeBottomBar(
                selectedTab = state.selectedTab,
                onTabSelected = { tab ->
                    onAction(HomeAction.OnTabSelected(tab))
                }
            )
        },
        floatingActionButton = {
            if (state.showFab) {
                HomeFab(
                    onClick = {
                        onAction(HomeAction.OnFabClick)
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        HomeNavigationWrapper(
            modifier = Modifier.padding(paddingValues)
        )
    }

    val context = LocalContext.current
    if (state.showExitDialog) {
        val activity = context as? Activity
        ExitAppDialog(
            onConfirm = {
                onAction(HomeAction.OnExitConfirmed)
                activity?.finish() // Cerrar la app
            },
            onDismiss = {
                onAction(HomeAction.OnExitCancelled)
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        HomeScreen(
            state = HomeState(),
            onAction = {}
        )
    }
}

