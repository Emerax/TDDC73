package com.example.thirdtimesthecharm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.erima668.tddc73.passwordStrengthMeter.implementations.EPasswordCriteriaVisualiserMode
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriteria
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriteriaVisualizer
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordCriterion
import com.erima668.tddc73.passwordStrengthMeter.implementations.PasswordStrengthBar
import com.erima668.tddc73.passwordStrengthMeter.PasswordStrengthMeter
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
            Column {
                Text("Yes hello I am content")
                Row {
                    val numberList : List<Int> = listOf(1, 2, 3)
                    for(number in numberList) {
                        Text(number.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun PasswordStrengthMeterPreview(modifier: Modifier = Modifier) {
    var password by remember { (mutableStateOf("")) }
    var passwordConfirmation by remember { (mutableStateOf("")) }
    val minLength = 6
    val goodLength = 13
    val passwordStrengthFloat = calculatePasswordStrength(password, minLength, goodLength)

    val forbiddenPasswords = setOf("password", "123", "admin", "secret", "password123")
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

    val strengthCriteria: List<(String) -> (Double)> = listOf(
        {
            val margin = (password.length - minLength).coerceAtLeast(0).toDouble()
            margin / (goodLength - minLength)
        },
        {
            if (it.any { c: Char -> c.isLowerCase() }) 1.0 else 0.0
        },
        {
            if (it.any { c: Char -> c.isUpperCase() }) 1.0 else 0.0
        },
        {
            if (it.any { c: Char -> c.isDigit() }) 1.0 else 0.0
        },
        {
            if (it.any { c: Char ->
                    c.isLowerCase()
                            || c.isUpperCase()
                            || c.isDigit()
                }) 0.0 else 1.0
        }
    )

    val criteria = PasswordCriteria(
        requiredCriteria
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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordConfirmation,
            onValueChange = { passwordConfirmation = it },
            label = { Text("Type password again") },
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
                    val fillFraction =
                        strengthCriteria.sumOf { criterion -> criterion(password) } / strengthCriteria.size
                    PasswordStrengthBar(
                        passwordStrength = fillFraction.toFloat(),
                        badColor = Color.Red,
                        goodColor = Color.Green,
                        modifier = Modifier.height(20.dp)
                    )
                    PasswordCriteriaVisualizer(
                        it,
                        mode = EPasswordCriteriaVisualiserMode.SHOW_FIRST_FAILED
                    )
                }
            },
            label = { Text("Criteria Strength:") }
        )
    }
}

fun calculatePasswordStrength(password: String, minLength: Int, goodLength: Int): Float {
    val margin = (password.length - minLength).coerceAtLeast(0).toFloat()
    return margin / (goodLength - minLength)
}