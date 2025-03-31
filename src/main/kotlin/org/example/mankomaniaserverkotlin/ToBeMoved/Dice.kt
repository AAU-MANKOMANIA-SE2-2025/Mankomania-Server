package com.example.mankomania_client.model

import kotlin.random.Random

class Dice {

    // roll dice method that rolls random number from 1 to 6
    fun roll(): Int {
        val die1 = Random.nextInt(1,7) // 1 inclusive to 7 exclusive --> 1 to 6
        val die2 = Random.nextInt(1,7)
        return die1 + die2
    }
}