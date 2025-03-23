/**
 * @file Dice.kt
 * @author 
 * @since 
 * @description Implementiert die Würfellogik unter Verwendung eines DiceStrategy.
 */
package org.example.mankomaniaserverkotlin.util

class Dice(private val strategy: DiceStrategy) {
    fun rollDice(): Int = strategy.roll()
}