package org.example.mankomaniaserverkotlin.service

import org.example.mankomaniaserverkotlin.model.Lottery
import org.example.mankomaniaserverkotlin.model.Player
import org.springframework.stereotype.Service

@Service
class LotteryService {
    private val lotteryPool = Lottery()
    private val winners = mutableSetOf<String>()

    fun processGoToField(player: Player): Boolean {
        if (isWinner(player)) return false
        return processPayment(player, 5000, "minigame entry")
    }

    fun processPassingLottery(player: Player): Boolean {
        if (isWinner(player)) return false
        return processPayment(player, 5000, "passed lottery field")
    }

    fun processLandingOnLottery(player: Player): LotteryResult {
        if (isWinner(player)) return LotteryResult(false, "Player has already won")
        if (player.balance <= 0) {
            declareWinner(player)
            return LotteryResult(false, "Player has won the game")
        }

        return if (!lotteryPool.isEmpty()) {
            val amount = lotteryPool.takeFromPool()
            player.balance += amount
            LotteryResult(true, "Won $amount from lottery!")
        } else {
            if (player.balance >= 50000) {
                player.balance -= 50000
                lotteryPool.addToPool(50000)
                LotteryResult(true, "Paid 50000")
            } else {
                declareWinner(player)
                LotteryResult(false, "Player has won the game")
            }
        }
    }

    private fun processPayment(player: Player, amount: Int, reason: String): Boolean {
        if (player.balance >= amount) {
            player.balance -= amount
            lotteryPool.addToPool(amount)

            // Check if payment made player bankrupt
            if (player.balance <= 0) {
                declareWinner(player)
            }
            return true
        }
        return false
    }

    private fun isWinner(player: Player): Boolean {
        return player.balance <= 0 || winners.contains(player.id)
    }

    private fun declareWinner(player: Player) {
        winners.add(player.id)
        player.balance = 0 // Ensure balance is zero
    }

    fun getPoolAmount(): Int = lotteryPool.getPoolAmount()
    fun getWinners(): Set<String> = winners.toSet()

    data class LotteryResult(val success: Boolean, val message: String)
}