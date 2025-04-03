/**
 * @file Dice.kt
 * @author eles17
 * @since
 * @description responsible for rolling dice, delegating the actual roll logic to a strategy
 */
package org.example.mankomaniaserverkotlin.util

/**
 * The Dice class is responsible for rolling dice, delegating the actual roll logic to a strategy.
 */
class Dice(private val strategy: DiceStrategy) {
    /**
     * Rolls the dice using the provided strategy.
     *
     * @return The result of the dice roll according to the strategy.
     */
    fun roll(): Int = strategy.roll()

}