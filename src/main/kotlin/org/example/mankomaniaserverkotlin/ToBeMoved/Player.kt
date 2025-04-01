package com.example.mankomania_client.model

/**
 * @author eles17
 * Represents a player in the game.
 *
 * @property name The name of the player.
 * @property position The current position of the player on the board.
 */
data class Player(
    val name: String,
    var position: Int = 0
    ) {
    /**
     * Moves the player forward on the board by a given number of steps.
     * If the end of the board is reached, the player wraps around to the start.
     *
     * @param steps Number of steps to move forward.
     * @param board The game board, which provides the size for wrapping logic.
     */
    fun move(steps:Int, board:Board){
        position = (position + steps) % board.size //if on 40 --> wrap around back to field 1
    }

    /**
     * Returns the player's current position on the board.
     *
     * @return The current field index.
     */
    fun getCurrentPosition(): Int {
        return position
    }

}