package com.erima668.tddc73.passwordStrengthMeter.implementations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

@Composable
fun PasswordStrengthBar(
    passwordStrength: Float,
    badColor: Color,
    goodColor: Color,
    modifier: Modifier = Modifier
) {
    val color by animateColorAsState(lerp(badColor, goodColor, passwordStrength))
    val fraction by animateFloatAsState(passwordStrength)

    Box(
        modifier
            .fillMaxWidth(fraction)
            .background(color)
    )
}