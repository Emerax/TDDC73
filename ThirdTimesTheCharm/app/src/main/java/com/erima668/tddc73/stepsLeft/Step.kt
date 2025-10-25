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

fun <T : Step> stepsProgress(steps: Collection<T>): Float {
    if(steps.size <= 1) {
        return 1f;
    }

    val complete: Int = steps.count{s -> s.complete()}
    val sizeExcludingLast: Int = steps.size - 1
    return complete / sizeExcludingLast.toFloat()
}