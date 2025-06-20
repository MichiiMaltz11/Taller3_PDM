package com.example.testeableapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.action.ViewActions.swipeRight
import com.example.testeableapp.ui.Screens.TipCalculatorScreen
import org.junit.Rule
import org.junit.Test

class TipCalculatorUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    //Test 1: Redondear propina y validar cambio en el calculo
    @Test
    fun redondearPropina_validaCambioCalculo() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Arrange
        composeTestRule.onNodeWithText("Monto de la cuenta").performTextInput("84.5")

        // Act
        composeTestRule.onNodeWithText("Redondear propina").performClick()

        // Assert (84.5 * 0.15 = 12.675 → 13.0)
        composeTestRule.onNodeWithText("Propina: \$13.00").assertExists()
    }

    //Test 2: Cambiar slider de porcentaje y verificar calculo
    @Test
    fun cambiarSliderPorcentaje_verificaCalculo() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        // Arrange
        composeTestRule.onNodeWithText("Monto de la cuenta").performTextInput("100")

        // Act
        composeTestRule.onNode(hasText("Porcentaje de propina: 15%")).performTouchInput {
            swipeRight() // simula movimiento del slider
        }

        // Assert (ej: si llega a 20%, 100 * 0.20 = 20.00)
        composeTestRule.waitUntil(timeoutMillis = 2000) {
            composeTestRule.onAllNodesWithText("Propina: \$20.00").fetchSemanticsNodes().isNotEmpty()
        }
    }

    //Test 3: Validar que existan los campos monto, porcentaje y número de personas
    @Test
    fun elementosUI_estanVisibles() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        composeTestRule.onNodeWithText("Monto de la cuenta").assertExists()
        composeTestRule.onNodeWithText("Porcentaje de propina: 15%").assertExists()
        composeTestRule.onNodeWithText("Número de personas: 1").assertExists()
    }


    @Test
    //Test adicional 1: Validar que el botón "-" no reduzca el número de personas por debajo de 1
    fun testNoDecrementBelowOnePerson() {
        composeTestRule.setContent {
            TipCalculatorScreen()
        }

        val decrementButton = composeTestRule.onNodeWithText("-")
        repeat(5) { decrementButton.performClick() }

        composeTestRule.onNodeWithText("1").assertIsDisplayed()
    }

    @Test
    //Test adicional 2: Validar que el campo “Monto de la cuenta” acepta solo números

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
