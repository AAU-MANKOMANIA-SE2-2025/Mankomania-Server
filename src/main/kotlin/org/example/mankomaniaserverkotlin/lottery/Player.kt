package org.example.mankomaniaserverkotlin.lottery

data class Player(
    val name: String,
    var balance: Int,
    var hasWon: Boolean = false
) {
    fun canAfford(amount: Int): Boolean = balance >= amount // liquidity check

    fun deduct(amount: Int) {
        require(canAfford(amount))
        balance -= amount
        checkIfWon()
    }

    fun add(amount: Int) {
        require(amount > 0)
        balance += amount
    }

    private fun checkIfWon() {
        hasWon = balance <= 0
    }
}