package org.christophertwo.quote.feature.auth.presentation

import org.christophertwo.quote.feature.auth.domain.model.OnboardingPage

data class AuthState(
    val onboardingPages: List<OnboardingPage> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLastPage: Boolean = false
)