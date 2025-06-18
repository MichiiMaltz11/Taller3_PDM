package com.example.testeableapp

import org.junit.Test
import kotlin.math.ceil
import org.junit.Assert.assertEquals

class TipCalculatorTest {

    // Funcion a testear
    private fun calculateTip(amount: Double, tipPercent: Int, roundUp: Boolean): Double {
        if (amount < 0) return 0.0
        var tip = amount * tipPercent / 100
        if (roundUp) {
            tip = ceil(tip)
        }
        return tip
    }
    //funcion para calcular el total por persona (en lugar de la variable totalPerPerson)
    private fun calcularTotalPorPersona(amount: Double, tipPercent: Int, roundUp: Boolean, personas: Int): Double {
        val tip = calculateTip(amount, tipPercent, roundUp)
        return if (personas > 0) (amount + tip) / personas else 0.0
    }


     //Test 1: Calculo con 20% de propina sin redondeo
    @Test
    fun testCalculo20PorcientoSinRedondeo() {
        // Arrange
        val monto = 100.0
        val porcentaje = 20
        val redondear = false

        // Act
        val resultado = calculateTip(monto, porcentaje, redondear)

        // Assert
        assertEquals(20.0, resultado, 0.01)
    }

    //Test 2: Calculo con 15% de propina y redondeo
    @Test
    fun testCalculo15PorcientoConRedondeo() {
        // Arrange
        val monto = 85.0
        val porcentaje = 15
        val redondear = true

        // Act
        val resultado = calculateTip(monto, porcentaje, redondear)

        // Assert
        // 85 * 0.15 = 12.75 → redondeado = 13.0
        assertEquals(13.0, resultado, 0.01)
    }

    //Test 3: Monto negativo, debe devolver 0
    @Test
    fun testMontoNegativo() {
        // Arrange
        val monto = -50.0
        val porcentaje = 20
        val redondear = false

        // Act
        val resultado = calculateTip(monto, porcentaje, redondear)

        // Assert
        assertEquals(0.0, resultado, 0.01)
    }

    //Test 4: Total por persona con propina
    @Test
    fun testTotalPorPersona() {
        // Arrange
        val monto = 90.0
        val porcentaje = 10
        val redondear = false
        val personas = 3

        // Act
        val resultado = calcularTotalPorPersona(monto, porcentaje, redondear, personas)

        // Assert
        // 90 + 9 = 99 → 99 / 3 = 33
        assertEquals(33.0, resultado, 0.01)
    }
}
