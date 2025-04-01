package com.example.mankomania_client.model

/**
 * @author eles17
 * Represents a single field on the game board.
 *
 * @property index The position of this field on the board (0-based).
 * @property hasBranch Indicates whether the field allows the player to choose a different path (branch).
 */
data class Field(val index:Int, val hasBranch: Boolean, val branchOptions: List<Int> = emptyList())
