package org.example.mankomaniaserverkotlin

import org.example.mankomaniaserverkotlin.util.Dice
import kotlin.test.assertTrue
import org.springframework.test.util.AssertionErrors.assertTrue
import kotlin.test.Test


/**
 * @author eles17
 * Unit test class for the [Dice] functionality.
 */
class DiceTest {

    /**
     * Validates that the vlaid value for dice Roll is returned
     */
    @Test
    fun testRollDiceReturnsValidValue(){
        val dice = Dice() // HAS PARAMETER NOW CHECK :...
        repeat(100){
            val result = dice.roll()
            assertTrue("Dice roll should be between 2 and 12, got $result", result in 2..12)
        }
    }
}