package org.christophertwo.quote.feature.home.presentation

import org.christophertwo.quote.feature.navigation.routes.AppTab

data class HomeState(
    val selectedTab: AppTab = AppTab.Screen1,
    val snackbarMessage: String? = null,
    val showFab: Boolean = true,
    val topBarTitle: String = "Home",
    val showExitDialog: Boolean = false
)