package org.christophertwo.quote.feature.home.presentation

import org.christophertwo.quote.feature.navigation.routes.AppTab

data class HomeState(
    val selectedTab: AppTab = AppTab.Quote,
    val snackbarMessage: String? = null,
    val showFab: Boolean = true,
    val topBarTitle: String = selectedTab.label,
    val showExitDialog: Boolean = false
)