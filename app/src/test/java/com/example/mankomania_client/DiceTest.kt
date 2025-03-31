package com.example.mankomania_client

import com.example.mankomania_client.model.Dice
import org.junit.Assert.assertTrue
import org.junit.Test

class DiceTest {

    @Test
    fun testRollDiceReturnsValidValue(){
        val dice = Dice()
        repeat(100){
            val result = dice.roll()
            assertTrue("Dice roll should be between 2 and 12, got $result", result in 2..12)
        }
    }
}