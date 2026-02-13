package org.override.tamplete.feature.navigation.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavKey
import org.override.tamplete.feature.navigation.routes.AppTab
import org.override.tamplete.feature.navigation.routes.RouteHome

class HomeNavigator {
    var currentTab by mutableStateOf(AppTab.Screen1)
        private set

    private val stacks = mapOf(
        AppTab.Screen1 to mutableStateListOf<NavKey>(RouteHome.Pantalla1)
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

        if (currentTab != AppTab.Screen1) {
            currentTab = AppTab.Screen1
            return true
        }

        return false
    }
}