/**
 * @file DefaultDiceStrategy.kt
 * @author eles17
 * @since
 * @description responsible for rolling dice, delegating the actual roll logic to a strategy.
 */
package org.example.mankomaniaserverkotlin.util

import kotlin.random.Random
/**
 * Default dice roll strategy using Random.nextInt() to simulate rolling two dice.
 */
class DefaultDiceStrategy : DiceStrategy {
    override fun roll(): Int{
        val die1 = Random.nextInt(1,7) //Rolls between 1 and 6
        val die2 = Random.nextInt(1,7) //Rolls between 1 and 6
        return die1 + die2
    }
}