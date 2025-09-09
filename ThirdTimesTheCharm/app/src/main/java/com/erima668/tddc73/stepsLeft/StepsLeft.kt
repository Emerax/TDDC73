package com.erima668.tddc73.stepsLeft

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.thirdtimesthecharm.ui.theme.ThirdTimesTheCharmTheme

@Composable
@Preview
fun StepsLeftPreview() {
    var step1: Boolean by remember { mutableStateOf(false) }
    var step2: Boolean by remember { mutableStateOf(false) }
    val steps: List<StepWithTitle> =
        listOf(
            StepWithTitle(
                "Title 1",
                {
                    Column() {
                        Text("Content 1")
                        Button(onClick = {step1 = true}) {
                            Text("Complete step 1")
                        }
                    }
                },
            ) { step1 },
            StepWithTitle(
                "Title 2",
                {
                    Column {
                        Text("Content 2")
                        Button(onClick = {step2 = true}) {
                            Text("Complete step 2")
                        }
                    }
                },
            ) { step2 }
        )

    ThirdTimesTheCharmTheme {
        Surface {
            Column {
                StepsLeft(
                    steps,
                    { _, s -> Text("This is step ${s.title}") },
                    { stepVisuals, modifier, content ->
                        HorizontalStepsLeftLayout(
                            stepVisuals,
                            content,

                            modifier
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun HorizontalStepsLeftLayout(
    stepVisuals: Unit,
    content: Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        content
        stepVisuals
    }
}

@Composable
fun <T : Step> StepsLeft(
    steps: List<T>,
    stepVisuals: @Composable (stepCount: Int, step: T) -> Unit,
    layout: @Composable (stepVisuals: Unit, modifier: Modifier, contentFunction: Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    if (steps.isNotEmpty()) {
        val stepIndex = steps.indexOfFirst { !it.complete() }
        if (stepIndex != -1) {
            val step = steps[stepIndex]
//            stepVisuals(stepIndex, step)
//            step.content()
            layout(
                stepVisuals(stepIndex, step),
                modifier,
                step.content()
            )

        } else {
            Text("ERROR: StepsLeft failed to find non-finished step!")
        }
    } else {
        Text("ERROR: StepsLeft component has empty steps!")
    }
}