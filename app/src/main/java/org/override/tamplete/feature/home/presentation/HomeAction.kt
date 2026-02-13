package org.override.tamplete.feature.home.presentation

import org.override.tamplete.feature.navigation.routes.AppTab

sealed interface HomeAction {
    data class OnTabSelected(val tab: AppTab) : HomeAction
    data object OnFabClick : HomeAction
    data object OnSnackbarDismissed : HomeAction
    data object OnSettingsClick : HomeAction
    data object OnBackPressed : HomeAction
    data object OnExitConfirmed : HomeAction
    data object OnExitCancelled : HomeAction
}