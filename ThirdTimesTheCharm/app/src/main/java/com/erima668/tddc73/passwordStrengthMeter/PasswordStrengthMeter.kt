package com.erima668.tddc73.passwordStrengthMeter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> PasswordStrengthMeter(
    passwordStrength: T,
    visualization: @Composable (T) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null
) {
    Column(modifier) {
        if (label != null) {
            label()
        }
        visualization(passwordStrength)
    }
}