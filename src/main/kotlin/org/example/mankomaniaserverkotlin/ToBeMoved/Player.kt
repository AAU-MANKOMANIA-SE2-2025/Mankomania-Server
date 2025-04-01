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
     * After moving, returns whether the new field is a branching field.
     *
     * @param steps Number of steps to move forward.
     * @param board The game board used for size and field information.
     * @return True if the player landed on a branching field; false otherwise.
     */
    fun move(steps:Int, board:Board): Boolean{
        position = (position + steps) % board.size //if on 40 --> wrap around back to field 1
        return hasBranch(board)
    }

    /**
     * Checks whether the player is currently on a branching field.
     *
     * @param board The game board used to access field information.
     * @return True if the current field has a branch; false otherwise.
     */
    fun hasBranch(board:Board): Boolean{
        return board.getField(position).hasBranch
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