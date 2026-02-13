package org.override.tamplete.feature.auth.presentation

import org.override.tamplete.feature.auth.domain.model.OnboardingPage

data class AuthState(
    val onboardingPages: List<OnboardingPage> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLastPage: Boolean = false
)