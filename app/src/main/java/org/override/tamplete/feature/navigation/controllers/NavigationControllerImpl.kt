package org.override.tamplete.feature.navigation.controllers

import androidx.navigation3.runtime.NavKey
import org.override.tamplete.feature.navigation.navigator.GlobalNavigator
import org.override.tamplete.feature.navigation.navigator.HomeNavigator
import org.override.tamplete.feature.navigation.routes.AppTab
import org.override.tamplete.feature.navigation.routes.RouteGlobal

class NavigationControllerImpl(
    private val globalNavigator: GlobalNavigator,
    private val homeNavigator: HomeNavigator,
) : NavigationController {
    override fun navigateTo(route: RouteGlobal) {
        globalNavigator.navigateTo(route)
    }

    override fun back(): Boolean {
        globalNavigator.back()
        return globalNavigator.rootBackStack.isNotEmpty()
    }

    override fun backTo(route: RouteGlobal) {
        globalNavigator.backTo(route)
    }

    override fun clearAndNavigateTo(route: RouteGlobal) {
        globalNavigator.clearAndNavigateTo(route)
    }

    override fun switchTab(tab: AppTab) {
        homeNavigator.switchTab(tab)
    }

    override fun navigateInTab(route: NavKey) {
        homeNavigator.navigateTo(route)
    }

    override fun backInTab(): Boolean {
        return homeNavigator.back()
    }

    override fun getCurrentTab(): AppTab {
        return homeNavigator.currentTab
    }
}