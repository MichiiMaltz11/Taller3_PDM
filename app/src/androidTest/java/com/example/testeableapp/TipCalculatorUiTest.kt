package com.example.testeableapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import org.junit.Rule
import org.junit.Test

class TipCalculatorUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    //Validar que el botón "-" no reduzca el número de personas por debajo de 1
    fun testNoDecrementBelowOnePerson() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        val decrementButton = composeTestRule.onNodeWithText("-")
        repeat(5) { decrementButton.performClick() }

        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }
    @Test
    //Validar que el campo “Monto de la cuenta” acepta solo números

    fun testInvalidInputInBillAmountField() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule
            .onNodeWithText("Monto de la cuenta")
            .performTextInput("abc")

        composeTestRule
            .onNodeWithText("Propina: $0.00")
            .assertIsDisplayed()
    }
}