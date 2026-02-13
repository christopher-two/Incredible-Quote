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
fun PrivacyPolicyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Privacy Policy",
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
            text = "1. Information We Collect",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We collect information you provide directly to us, such as when you create an account, " +
                    "update your profile, or use our services. This may include:\n\n" +
                    "• Name and email address\n" +
                    "• Profile information\n" +
                    "• Preferences and settings\n" +
                    "• Usage data and analytics",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "2. How We Use Your Information",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We use the information we collect to:\n\n" +
                    "• Provide, maintain, and improve our services\n" +
                    "• Process your transactions\n" +
                    "• Send you technical notices and support messages\n" +
                    "• Respond to your comments and questions\n" +
                    "• Analyze usage patterns and trends",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "3. Data Security",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We take reasonable measures to help protect your personal information from loss, theft, " +
                    "misuse, unauthorized access, disclosure, alteration, and destruction.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "4. Data Retention",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We store your personal information for as long as necessary to provide our services " +
                    "and comply with legal obligations.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "5. Your Rights",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You have the right to:\n\n" +
                    "• Access your personal data\n" +
                    "• Correct inaccurate data\n" +
                    "• Request deletion of your data\n" +
                    "• Object to processing of your data\n" +
                    "• Export your data",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "6. Contact Us",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "If you have any questions about this Privacy Policy, please contact us at:\n\n" +
                    "Email: privacy@example.com\n" +
                    "Address: 123 Privacy St, City, Country",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

