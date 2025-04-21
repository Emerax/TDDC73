package com.erima668.tddc73.passwordStrengthMeter.implementations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PasswordCriteriaVisualizer(
    criteriaResults: List<PasswordCriterionResult>,
    modifier: Modifier = Modifier,
    mode: EPasswordCriteriaVisualiserMode? = EPasswordCriteriaVisualiserMode.SHOW_ALL
) {
    when (mode) {
        EPasswordCriteriaVisualiserMode.SHOW_ALL -> {
            Column(modifier) {
                for (result in criteriaResults) {
                    Row {
                        val image = if (result.passed) Icons.Default.Check else Icons.Default.Close
                        val color = if (result.passed) Color.Green else Color.Red
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

        EPasswordCriteriaVisualiserMode.SHOW_FAILED -> {
            Column(modifier) {
                for (result in criteriaResults) {
                    if (!result.passed) {
                        Row {
                            val image = Icons.Default.Close
                            val color = Color.Red
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
        }

        EPasswordCriteriaVisualiserMode.SHOW_FIRST_FAILED -> {
            val result = criteriaResults.firstOrNull { !it.passed }
            if (result != null) {
                Row(modifier) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "A checkmark indicating this password criterion is fulfilled.",
                        tint = Color.Red
                    )
                    Text(result.formulation)
                }
            }
        }

        else -> Text("ERROR: Received unknown mode type \"${mode}\"")
    }
}

enum class EPasswordCriteriaVisualiserMode {
    SHOW_ALL, SHOW_FAILED, SHOW_FIRST_FAILED
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
