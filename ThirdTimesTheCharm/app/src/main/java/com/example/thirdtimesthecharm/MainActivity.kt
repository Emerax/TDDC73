package com.example.thirdtimesthecharm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thirdtimesthecharm.ui.theme.ThirdTimesTheCharmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdTimesTheCharmTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        //App goes here
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
        Surface {
            var password by remember { (mutableStateOf("")) }
            val minLength = 6
            val goodLength = 13
            val passwordStrengthFloat = calculatePasswordStrength(password, minLength, goodLength)

            val forbiddenPasswords = setOf("password", "123", "admin", "secret", "password123")
            val criteria = PasswordCriteria(
                listOf(
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
//                    PasswordCriterion("Passwords MUST match!") {
//                        //TODO: Implement me!
//                        true
//                    },
                    PasswordCriterion("Your chosen password is too common!") {
                        !forbiddenPasswords.contains(it.lowercase())
                    }
                )
            )

            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                PasswordStrengthMeter(
                    passwordStrengthFloat,
                    {
                        Column {
                            PasswordStrengthBar(
                                modifier = Modifier.height(20.dp),
                                passwordStrength = it,
                                badColor = Color.Red,
                                goodColor = Color.Green
                            )
                            when {
                                it <= 0f -> Text("Password MUST be at least $minLength characters long!")
                                it <= 1f -> Text("Password should be at least $goodLength characters long")
                                else -> {}
                            }
                        }
                    },
                    label = { Text("Password Strength:") },
                )
                Spacer(Modifier.height(16.dp))
                PasswordStrengthMeter(
                    criteria.resultForPassword(password),
                    {
                        Column {
                            val fillFraction = it.count { it.passed } / it.size.toFloat()
                            PasswordStrengthBar(
                                passwordStrength = fillFraction,
                                badColor = Color.Red,
                                goodColor = Color.Green,
                                modifier = Modifier.height(20.dp)
                            )
                            PasswordCriteriaVisualizer(it)
                        }
                    },
                    label = {Text("Criteria Strength:")}
                )
            }
        }
    }
}

@Composable
fun PasswordCriteriaVisualizer(criteriaResults: List<PasswordCriterionResult>, modifier: Modifier = Modifier) {
    Column(modifier) {
        for (result in criteriaResults) {
            Row {
                val image = if(result.passed) Icons.Default.Check else Icons.Default.Close
                val color = if(result.passed) Color.Green else Color.Red
                Icon(
                    image,
                    contentDescription = "A checkmark indicating this password criterion is fulfilled.",
                    tint = color
                )
                Text(result.formulation)
            }
        }
    }
}

class PasswordCriteria(private val criteria: List<PasswordCriterion>) {
    fun resultForPassword(password: String): List<PasswordCriterionResult> {
        return criteria.map { PasswordCriterionResult(it.formulation, it.criterionCheck(password)) }
    }
}

data class PasswordCriterionResult(
    val formulation: String,
    val passed: Boolean,
)

data class PasswordCriterion(
    val formulation: String,
    val criterionCheck: (String) -> (Boolean),
)

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


fun calculatePasswordStrength(password: String, minLength: Int, goodLength: Int): Float {
    val margin = (password.length - minLength).coerceAtLeast(0).toFloat()
    return margin / (goodLength - minLength)
}