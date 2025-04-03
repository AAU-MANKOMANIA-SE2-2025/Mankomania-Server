package org.example.mankomaniaserverkotlin.model

/**
 * @author eles17
 * Represents a player in the game.
 *
 * @property name The name of the player.
 * @property position The current position of the player on the board.
 */
data class Player(
    val name: String,
    var position: Int = 0,
    var balance: Int = 0
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
    fun move(steps:Int, board: Board): Boolean{
        require(steps >= 0) {"Steps must be non-negative."}

        position = (position + steps) % board.size //if on 40 --> wrap around back to field 1

        //retrieve the field once and check for branch
        val currentField = board.getField(position) //Board.getField(position) must return a Field with populated branchOptions if hasBranch == true
        return if(currentField.hasBranch) {
            chooseBranch(currentField.branchOptions)
            true
        }else{
            false
        }

    }

    /**
     * Checks whether the player is currently on a branching field.
     *
     * @param board The game board used to access field information.
     * @return True if the current field has a branch; false otherwise.
     */
    fun hasBranch(board: Board): Boolean{
        return board.getField(position).hasBranch
    }

    /**
     * Simulates a branch choice when landing on a branching field.
     *
     * @param branchOptions A list of possible next field indices to choose from
     */
    fun chooseBranch(branchOptions: List<Int>){
        //simulate a basic decision: choose the first option
        val chosen = branchOptions.firstOrNull()
        if(chosen != null) {
            println("Branching: Player '$name' chooses to go to field $chosen.")
            position = chosen
        }else{
            println("Branching: No branch options available for Player '$name'.")
        }
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