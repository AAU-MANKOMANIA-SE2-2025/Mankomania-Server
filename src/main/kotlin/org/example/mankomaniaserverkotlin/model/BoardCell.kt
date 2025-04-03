/**
 * @file BoardCell.kt
 * @author eles17
 * @since 
 * @description Repräsentiert ein einzelnes Spielfeld (Zelle) inkl. Position und zugewiesener Action.
 */
package org.example.mankomaniaserverkotlin.model
/**
 * @property index The position of this field on the board (0-based).
 * @property hasBranch Indicates whether the field allows the player to choose a different path (branch).
 */
data class BoardCell(val index:Int, val hasBranch: Boolean, val branchOptions: List<Int> = emptyList())
