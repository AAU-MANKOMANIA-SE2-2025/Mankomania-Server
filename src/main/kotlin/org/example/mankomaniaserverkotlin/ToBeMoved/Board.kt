package com.example.mankomania_client.model

/**
 * @author eles17
 *  * Represents the game board composed of multiple fields.
 *
 * @property size The total number of fields on the board.
 * @param isBranchField A function that determines whether a given field index should be a branching field.
 *  Used to initialize the board with correct branch assignments.
 */

class Board (val size: Int, isBranchField: (Int) -> Boolean) {
    val fields: List<Field> = List(size) { index ->
        Field(index, hasBranch = isBranchField(index))
    }

    fun getField(index:Int): Field = fields [index % fields.size]
}