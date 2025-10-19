package com.erima668.tddc73.stepsLeft

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirdtimesthecharm.ui.theme.ThirdTimesTheCharmTheme

@Composable
@Preview
fun StepsLeftPreview() {
    var step1: Boolean by remember { mutableStateOf(false) }
    var step2: Boolean by remember { mutableStateOf(false) }
    var step3: Boolean by remember { mutableStateOf(false) }
    val steps: List<StepWithTitle> =
        listOf(
            StepWithTitle(
                "Title 1",
                {
                    Column {
                        Text("Content 1")
                        Button(onClick = { step1 = true }) {
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
                        Button(onClick = { step2 = true }) {
                            Text("Complete step 2")
                        }
                    }
                },
            ) { step2 },
            StepWithTitle(
                "Title 3",
                {
                    Column {
                        Text("Content 3")
                        Button(onClick = { step3 = true }) {
                            Text("Complete step 3")
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
                    content = {
                        stepIndex, step ->
                        Column {
                            StepIndication(stepIndex, steps.size)
                            step.content()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StepIndication(index: Int, max: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(8.dp)) {
        for (i in 0..< max) {
            val color: Color = if(i == index) Color.Blue else Color.Gray
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .drawBehind {
                        drawCircle(
                            color = color,
                            radius = this.size.maxDimension
                        )
                    },
                text = "${i + 1}"
            )
        }
    }
}

@Composable
fun VerticalStepsLeftLayout(
    stepVisuals: () -> Unit,
    content: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        stepVisuals()
        content()
    }
}

@Composable
fun <T : Step> StepsLeft(
    steps: List<T>,
    modifier: Modifier = Modifier,
    content: @Composable (stepIndex: Int, step: T) -> Unit
) {
    if (steps.isNotEmpty()) {
        val stepIndex = steps.indexOfFirst { !it.complete() }
        if (stepIndex != -1) {
            val step = steps[stepIndex]
            content(stepIndex, step)
        } else {
            Text("ERROR: StepsLeft failed to find non-finished step!")
        }
    } else {
        Text("ERROR: StepsLeft component has empty steps!")
    }
}