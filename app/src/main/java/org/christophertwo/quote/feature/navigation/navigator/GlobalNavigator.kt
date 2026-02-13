package org.christophertwo.quote.feature.navigation.navigator

import android.util.Log
import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.christophertwo.quote.feature.auth.domain.repository.UserRepository
import org.christophertwo.quote.feature.navigation.routes.RouteGlobal

@OptIn(DelicateCoroutinesApi::class)
class GlobalNavigator(
    private val userRepository: UserRepository
) {
    private val _rootBackStack: MutableStateFlow<List<NavKey>> = MutableStateFlow(emptyList())
    val rootBackStack: StateFlow<List<NavKey>> = _rootBackStack.asStateFlow()

    init {
        GlobalScope.launch {
            val isLoggedIn = userRepository.isLoggedIn().firstOrNull()
            if (isLoggedIn == true) {
                _rootBackStack.update { listOf(RouteGlobal.Home) }
            } else {
                _rootBackStack.update { listOf(RouteGlobal.Auth) }
            }
        }.invokeOnCompletion {
            Log.d("GlobalNavigator", "initialized: ${rootBackStack.value}")
        }
    }

    fun back() {
        if (rootBackStack.value.isEmpty()) return
        _rootBackStack.update { it.dropLast(1) }
    }

    fun back(
        route: RouteGlobal
    ) {
        if (rootBackStack.value.isEmpty()) return
        _rootBackStack.update { it.filter { item -> item != route } }
    }

    fun clear() {
        _rootBackStack.update { emptyList() }
    }

    fun navigateTo(route: RouteGlobal) {
        _rootBackStack.update { it + route }
    }

    fun backTo(targetScreen: NavKey) {
        if (_rootBackStack.value.isEmpty()) return
        if (_rootBackStack.value.lastOrNull() == targetScreen) return
        val targetIndex = _rootBackStack.value.indexOfLast { it == targetScreen }
        if (targetIndex == -1) return
        _rootBackStack.update { it.take(targetIndex + 1) }
    }

    fun clearAndNavigateTo(route: RouteGlobal) {
        clear()
        navigateTo(route)
    }
}
