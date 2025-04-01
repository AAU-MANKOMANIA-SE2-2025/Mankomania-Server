package com.example.mankomania_client.model

import kotlin.random.Random



/**
 * @author eles17
 * Simulates a dice that generates random numbers between 1 and 6.
 */
class Dice {
    /**
    * Rolls the dice.
    *
    * @return A random integer between 1 and 6 (inclusive).
    */
    fun roll(): Int {
        val die1 = Random.nextInt(1,7) // 1 inclusive to 7 exclusive --> 1 to 6
        val die2 = Random.nextInt(1,7)
        return die1 + die2
    }
}