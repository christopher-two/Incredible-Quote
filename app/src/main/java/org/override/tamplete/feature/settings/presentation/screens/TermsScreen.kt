package org.override.tamplete.feature.settings.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TermsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Terms of Service",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Last updated: February 13, 2026",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "1. Acceptance of Terms",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "By accessing and using this application, you accept and agree to be bound by the terms " +
                    "and provision of this agreement.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "2. Use License",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Permission is granted to temporarily use this application for personal, non-commercial " +
                    "transitory viewing only. This is the grant of a license, not a transfer of title, and " +
                    "under this license you may not:\n\n" +
                    "• Modify or copy the materials\n" +
                    "• Use the materials for commercial purposes\n" +
                    "• Attempt to decompile or reverse engineer the software\n" +
                    "• Remove any copyright or proprietary notations\n" +
                    "• Transfer the materials to another person",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "3. Disclaimer",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The materials within this application are provided on an 'as is' basis. We make no " +
                    "warranties, expressed or implied, and hereby disclaim and negate all other warranties " +
                    "including, without limitation, implied warranties or conditions of merchantability, " +
                    "fitness for a particular purpose, or non-infringement of intellectual property.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "4. Limitations",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "In no event shall we or our suppliers be liable for any damages (including, without " +
                    "limitation, damages for loss of data or profit, or due to business interruption) arising " +
                    "out of the use or inability to use this application.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "5. Account Terms",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• You must be 13 years or older to use this service\n" +
                    "• You are responsible for maintaining account security\n" +
                    "• You are responsible for all activities under your account\n" +
                    "• You must not use the service for illegal purposes\n" +
                    "• We reserve the right to terminate accounts at our discretion",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "6. Modifications",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We may revise these terms of service at any time without notice. By using this " +
                    "application you are agreeing to be bound by the current version of these terms of service.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "7. Contact Information",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Questions about the Terms of Service should be sent to:\n\n" +
                    "Email: legal@example.com",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

