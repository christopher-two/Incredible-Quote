package org.christophertwo.quote.feature.navigation.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavKey
import org.christophertwo.quote.feature.navigation.routes.AppTab
import org.christophertwo.quote.feature.navigation.routes.RouteHome

class HomeNavigator {
    var currentTab by mutableStateOf(AppTab.Quote)
        private set

    private val stacks = mapOf(
        AppTab.Quote to mutableStateListOf<NavKey>(RouteHome.Quote),
        AppTab.Products to mutableStateListOf<NavKey>(RouteHome.Products)
    )

    val currentStack: List<NavKey>
        get() = stacks[currentTab] ?: emptyList()


    fun switchTab(tab: AppTab) {
        currentTab = tab
    }

    fun navigateTo(route: NavKey) {
        stacks[currentTab]?.add(route)
    }

    fun back(): Boolean {
        val activeStack = stacks[currentTab] ?: return false

        if (activeStack.size > 1) {
            activeStack.removeAt(activeStack.lastIndex)
            return true
        }

        if (currentTab != AppTab.Products) {
            currentTab = AppTab.Products
            return true
        }

        return false
    }
}