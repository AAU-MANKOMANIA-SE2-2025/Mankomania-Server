/**
 * @file DiceStrategy.kt
 * @author 
 * @since 
 * @description Interface für austauschbare Würfelstrategien (Strategy Pattern).
 */
package org.example.mankomaniaserverkotlin.util


interface DiceStrategy {
    fun roll(): Int
}