package com.example.mankomania_client.model

/**
 * @author eles17
 * Represents the game board made up of multiple fields.
 *
 * There are two ways to create a board:
 * 1. By passing a size and a rule that marks certain fields as branching.
 * 2. By passing a custom list of Field objects (used in tests or advanced setups).
 *
 * @property fields List of all fields on the board.
 * @property size Total number of fields (auto-calculated from the list).
*/

class Board (val fields: List<Field>) {

    val size: Int = fields.size

    constructor(size: Int, isBranchField: (Int) -> Boolean) : this(
        List(size) { index ->
            Field(index, hasBranch = isBranchField(index))
        }
    )

    /**
     * Returns the field at the specified index.
     * If the index is out of bounds, it wraps around using modulo to ensure a valid field is returned.
     *
     * @param index The index of the field to retrieve.
     * @return The field at the given index, wrapped around if necessary.
     */
    fun getField(index:Int): Field = fields [index % fields.size]
}