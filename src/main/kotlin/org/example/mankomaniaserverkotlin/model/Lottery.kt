package org.example.mankomaniaserverkotlin.model

class Lottery {
    private var pool: Int = 0

    fun addToPool(amount: Int) {
        require(amount > 0)
        pool += amount
    }

    fun takeFromPool(): Int {
        val amount = pool
        pool = 0
        return amount
    }

    fun getPoolAmount(): Int = pool

    fun isEmpty(): Boolean = pool == 0
}