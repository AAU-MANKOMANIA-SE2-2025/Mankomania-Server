/**
 * @file DiceStrategy.kt
 * @author eles17
 * @since 
 * @description Interface für austauschbare Würfelstrategien (Strategy Pattern).
 */
package org.example.mankomaniaserverkotlin.util

/**
 * Functional Interface for interchangeable dice rolling strategies.
 * The strategy defines how the dice are rolled.
 */
fun interface DiceStrategy {
    /**
     * Rolls the dice and returns the result.
     * @return The result of the dice roll.
     */
    fun roll(): Int
}