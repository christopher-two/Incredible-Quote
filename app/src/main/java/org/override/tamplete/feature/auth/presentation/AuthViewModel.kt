package org.override.tamplete.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.override.tamplete.feature.auth.domain.model.OnboardingDefaults
import org.override.tamplete.feature.auth.domain.usecase.LoginUseCase
import org.override.tamplete.feature.navigation.controllers.NavigationController
import org.override.tamplete.feature.navigation.routes.RouteGlobal

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val navigationController: NavigationController
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(AuthState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadOnboardingPages()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = AuthState()
        )

    private fun loadOnboardingPages() {
        _state.update { currentState ->
            val pages = OnboardingDefaults.getPages()
            currentState.copy(
                onboardingPages = pages,
                isLastPage = pages.size == 1
            )
        }
    }

    fun onAction(action: AuthAction) {
        when (action) {
            is AuthAction.OnPageChanged -> {
                _state.update { currentState ->
                    currentState.copy(
                        currentPage = action.page,
                        isLastPage = action.page == currentState.onboardingPages.size - 1
                    )
                }
            }

            AuthAction.OnLoginClick -> {
                performLogin()
            }

            AuthAction.OnSkipClick -> {
                performLogin()
            }
        }
    }

    private fun performLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            loginUseCase()
                .onSuccess { user ->
                    _state.update {
                        it.copy(isLoading = false)
                    }
                    navigationController.navigateTo(RouteGlobal.Home)
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Error al iniciar sesión"
                        )
                    }
                }
        }
    }
}