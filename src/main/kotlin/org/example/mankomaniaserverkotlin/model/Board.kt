package org.example.mankomaniaserverkotlin.model

/**
 * @author eles17
 * Represents the game board made up of multiple fields.
 *
 * There are two ways to create a board:
 * 1. By passing a size and a rule that marks certain fields as branching.
 * 2. By passing a custom list of Field objects (used in tests or advanced setups).
 *
 * @property cells List of all fields on the board.
 * @property size Total number of fields (auto-calculated from the list).
*/

class Board (val cells: List<BoardCell>) {

    val size: Int = cells.size

    constructor(size: Int, isBranchField: (Int) -> Boolean) : this(
        List(size) { index ->
            BoardCell(index, hasBranch = isBranchField(index))
        }
    )

    /**
     * Returns the field at the specified index.
     * If the index is out of bounds, it wraps around using modulo to ensure a valid field is returned.
     *
     * @param index The index of the field to retrieve.
     * @return The field at the given index, wrapped around if necessary.
     */
    fun getField(index:Int): BoardCell = cells [index % cells.size]
}