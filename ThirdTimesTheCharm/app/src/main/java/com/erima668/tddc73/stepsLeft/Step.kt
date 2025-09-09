package com.erima668.tddc73.stepsLeft

import androidx.compose.runtime.Composable

open class Step(
    val content: @Composable () -> Unit,
    val complete: () -> Boolean
)

class StepWithTitle(
    val title: String,
    content: @Composable () -> Unit,
    complete: () -> Boolean,
) : Step(content, complete)