package com.example.thirdtimesthecharm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.erima668.tddc73.passwordStrengthMeter.PasswordStrengthMeter
import com.erima668.tddc73.passwordStrengthMeter.implementations.EPasswordCriteriaVisualiserMode
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriteria
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriteriaVisualizer
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriterion
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriterionResult
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordStrengthBar
import com.erima668.tddc73.stepsLeft.Step
import com.erima668.tddc73.stepsLeft.StepIndication
import com.erima668.tddc73.stepsLeft.StepWithTitle
import com.erima668.tddc73.stepsLeft.StepsLeft
import com.erima668.tddc73.stepsLeft.stepsProgress
import com.example.thirdtimesthecharm.ui.theme.ThirdTimesTheCharmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdTimesTheCharmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        ThirdTimesTheCharmMain()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ThirdTimesTheCharmPreview() {
    ThirdTimesTheCharmTheme {
        ThirdTimesTheCharmMain()
    }
}

@Composable
fun ThirdTimesTheCharmMain() {
    var welcomeStepComplete: Boolean by remember { mutableStateOf(false) }
    var stepsLeftComplete: Boolean by remember { mutableStateOf(false) }
    var stepsLeftExamplesComplete: Boolean by remember { mutableStateOf(false) }
    var passwordComplete: Boolean by remember { mutableStateOf(false) }
    val steps: List<StepWithTitle> = listOf(
        StepWithTitle(
            "TDDC73 Mini SDK Presentation",
            content = {
                ThirdTimesTheCharmStep({ welcomeStepComplete = true }) {
                    Text(
                        "This app will serve to present an implementation" +
                                " of the two UI patterns, \"Steps Left\" and " +
                                "\"Password Strength Meter\", I've chosen for the " +
                                "course. Simply press \"Next\" to start the presentation."
                    )
                }
            },
            complete = { welcomeStepComplete }
        ),
        StepWithTitle(
            "StepsLeft Component",
            content = {
                ThirdTimesTheCharmStep({ stepsLeftComplete = true }) {
                    Column {
                        Text(
                            "First of all, the steps traversed " +
                                    "in this app are themselves an example of" +
                                    " the StepsLeft component I've developed. "
                        )
                        Text(
                            "The next page will display some alternative" +
                                    "ways that the component can be used."
                        )
                    }
                }
            },
            complete = { stepsLeftComplete }
        ),
        StepWithTitle(
            "StepsLeft Examples",
            content = {
                var checkbox1: Boolean by remember { mutableStateOf(false) }
                var checkbox2: Boolean by remember { mutableStateOf(false) }
                var checkbox3: Boolean by remember { mutableStateOf(false) }
                var sliderValue: Float by remember { mutableFloatStateOf(0f) }
                val exampleSteps: List<Step> = listOf(
                    Step(content = {
                        Column {
                            Text(" In this example, check all boxes to automatically progress.")
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(checkbox1, onCheckedChange = { v -> checkbox1 = v })
                                Checkbox(checkbox2, onCheckedChange = { v -> checkbox2 = v })
                                Checkbox(checkbox3, onCheckedChange = { v -> checkbox3 = v })
                            }
                        }
                    }) { checkbox1 && checkbox2 && checkbox3 },
                    Step(content = {
                        Column {
                            Text("Or like this, where you must drag the slider to atleast 80%.")
                            Slider(sliderValue, onValueChange = { v -> sliderValue = v })
                        }
                    })
                    { sliderValue >= 0.8f },
                    Step(content = {
                        Text("All done!")
                    }) { false }
                )

                var stepToDisplay: Int by remember { mutableIntStateOf(0) }
                val backAndForthSteps: List<Step> = listOf(
                    Step(content = {
                        Text("This is step $stepToDisplay!")
                    }) { stepToDisplay > 0 },
                    Step(content = {
                        Text("This is step $stepToDisplay!")
                    }) { stepToDisplay > 1 },
                    Step(content = {
                        Text("This is step $stepToDisplay!")
                    }) { stepToDisplay > 2 },
                    Step(content = {
                        Text("This is step $stepToDisplay!")
                    }) { stepToDisplay > 3 },
                    Step(content = {
                        Text("This is step $stepToDisplay!")
                    }) { false }
                )

                ThirdTimesTheCharmStep({ stepsLeftExamplesComplete = true }) {
                    Column {
                        Text("Content of each step can be easily swapped.")
                        StepsLeft(exampleSteps) { step: Step ->
                            step.content()
                        }
                        Text(
                            "\nVisuals of steps remaining are independent " +
                                    "of the steps themselves, so we can use any " +
                                    "component we want to display our progress."
                        )
                        CircularProgressIndicator(progress = { stepsProgress(exampleSteps) })
                        HorizontalDivider()
                        Text("Because of the above, it is also simple to enable steps that can go back or forth")
                        StepsLeft(backAndForthSteps) { step: Step ->
                            step.content()
                        }
                        Row {
                            Button(onClick = { (--stepToDisplay).coerceIn(0, 4) }) {
                                Text("Back")
                            }
                            Button(onClick = { (++stepToDisplay).coerceIn(0, 4) }) {
                                Text("Forward")
                            }
                        }
                        LinearProgressIndicator(progress = { stepsProgress(backAndForthSteps) })
                    }
                }
            },
            complete = { stepsLeftExamplesComplete }
        ),
        StepWithTitle(
            "PasswordStrengthMeter component",
            content = {
                var simplePassword by remember { (mutableStateOf("")) }
                val minLength = 6
                val goodLength = 13
                val passwordStrengthFloat =
                    calculatePasswordStrength(simplePassword, minLength, goodLength)
                ThirdTimesTheCharmStep({ passwordComplete = true }) {
                    Column {
                        Text("This page will showcase my PasswordStrengthMeter component.")
                        Text(
                            "There are two main ways to work with this component. A simpler" +
                                    " version that simply visualizes a given value using any provided" +
                                    " visualizer and a more complicated version that allows for more" +
                                    " complex requirements."
                        )
                        Text(
                            "The following is an example of the simpler version which only " +
                                    "reflects password length"
                        )
                        PasswordInput(simplePassword) { s -> simplePassword = s }
                        PasswordStrengthMeter(
                            passwordStrengthFloat,
                            visualization = { floatStrength ->
                                PasswordStrengthBar(
                                    floatStrength,
                                    Color.Red,
                                    Color.Green,
                                    Modifier.height(16.dp)
                                )
                            })
                        HorizontalDivider()

                        Spacer(Modifier.height(16.dp))

                        var password: String by remember { mutableStateOf("") }
                        var passwordConfirmation by remember { mutableStateOf("") }
                        val forbiddenPasswords =
                            setOf("password", "123", "admin", "secret", "password123")
                        val requiredCriteria: List<PasswordCriterion> = listOf(
                            PasswordCriterion("Password MUST be at least $minLength characters long") {
                                it.length > minLength
                            },
                            PasswordCriterion("Password MUST contain at least one lower case letter") {
                                it.any { c: Char -> c.isLowerCase() }
                            },
                            PasswordCriterion("Password MUST contain at least one upper case letter") {
                                it.any { c: Char -> c.isUpperCase() }
                            },
                            PasswordCriterion("Password MUST contain at least one number") {
                                it.any { c: Char -> c.isDigit() }
                            },
                            PasswordCriterion("Passwords MUST match!") {
                                it == passwordConfirmation
                            },
                            PasswordCriterion("Your chosen password is too common!") {
                                !forbiddenPasswords.contains(it.lowercase())
                            }
                        )
                        val criteria = PasswordCriteria(
                            requiredCriteria
                        )

                        Text(
                            "And here is an example of the more complex one. As a bonus, " +
                                    "it's requirements are quite self-explanatory."
                        )
                        PasswordInput(password) { password = it }
                        PasswordInput(passwordConfirmation) { passwordConfirmation = it }
                        val results: List<PasswordCriterionResult> =
                            criteria.resultForPassword(password)
                        PasswordStrengthMeter(
                            criteria.resultForPassword(password),
                            {
                                Column {
                                    val fillFraction =
                                        results.count { it.passed } / results.size.toFloat()
                                    PasswordStrengthBar(
                                        passwordStrength = fillFraction,
                                        badColor = Color.Red,
                                        goodColor = Color.Green,
                                        modifier = Modifier.height(20.dp)
                                    )
                                    PasswordCriteriaVisualizer(
                                        it,
                                        mode = EPasswordCriteriaVisualiserMode.SHOW_FIRST_FAILED
                                    )
                                }
                            }
                        )
                    }
                }
            },
            complete = { passwordComplete }
        ),
        StepWithTitle(
            "Complete",
            content = {
                Text("That's the end of the presentation!")
            },
            complete = { false }
        )
    )

    Surface {
        StepsLeft(
            steps,
            content = { stepIndex, step ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(step.title, fontSize = 30.sp, textAlign = TextAlign.Center)
                        HorizontalDivider()
                        step.content()
                    }

                    StepIndication(stepIndex, steps.size)
                }
            }
        )
    }
}

@Composable
fun ThirdTimesTheCharmStep(onNextClicked: () -> Unit, content: @Composable () -> Unit) {
    Column(modifier = Modifier.wrapContentHeight(), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
        content()
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onNextClicked) {
            Text("Next")
        }
    }
}

@Composable
fun PasswordInput(value: String, onValueChanged: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChanged,
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

fun calculatePasswordStrength(password: String, minLength: Int, goodLength: Int): Float {
    val margin = (password.length - minLength).coerceAtLeast(0).toFloat()
    return margin / (goodLength - minLength)
}

@Composable
fun PasswordStrengthMeterPreview(modifier: Modifier = Modifier) {

//    val strengthCriteria: List<(String) -> (Double)> = listOf(
//        {
//            val margin = (password.length - minLength).coerceAtLeast(0).toDouble()
//            margin / (goodLength - minLength)
//        },
//        {
//            if (it.any { c: Char -> c.isLowerCase() }) 1.0 else 0.0
//        },
//        {
//            if (it.any { c: Char -> c.isUpperCase() }) 1.0 else 0.0
//        },
//        {
//            if (it.any { c: Char -> c.isDigit() }) 1.0 else 0.0
//        },
//        {
//            if (it.any { c: Char ->
//                    c.isLowerCase()
//                            || c.isUpperCase()
//                            || c.isDigit()
//                }) 0.0 else 1.0
//        }
//    )


    Column(modifier = Modifier.padding(16.dp)) {
//        PasswordStrengthMeter(
//            passwordStrengthFloat,
//            {
//                Column {
//                    PasswordStrengthBar(
//                        modifier = Modifier.height(20.dp),
//                        passwordStrength = it,
//                        badColor = Color.Red,
//                        goodColor = Color.Green
//                    )
//                    when {
//                        it <= 0f -> Text("Password MUST be at least $minLength characters long!")
//                        it <= 1f -> Text("Password should be at least $goodLength characters long")
//                        else -> {}
//                    }
//                }
//            },
//            label = { Text("Password Strength:") },
//        )
        Spacer(Modifier.height(16.dp))
    }
}