package org.example.mankomaniaserverkotlin.service

import org.example.mankomaniaserverkotlin.model.Bet
import org.example.mankomaniaserverkotlin.model.HorseColor
import org.example.mankomaniaserverkotlin.model.Player
import org.springframework.stereotype.Service

@Service
class HorseRaceService {

    private val players = mutableMapOf<String, Player>()

    // This will be used to calculate the winner
    fun spinRoulette(): HorseColor {
        val weightedList = HorseColor.entries.flatMap { color -> List(color.weight) { color } }
        return weightedList.random()  // Return a random HorseColor from the weighted list
    }

    // Start the race and calculate payouts
    fun startRace(bets: List<Bet>, players: Map<String, Player>): Pair<HorseColor, Map<String, Int>> {
        val winner = spinRoulette()  // Get the winning horse
        val payouts = mutableMapOf<String, Int>()

        bets.forEach { bet ->
            if (bet.horse == winner) {  // Compare bet's horse with the winner
                // Handle payouts (e.g., double the bet amount for the win)
                payouts[bet.playerId] = bet.amount * 2
            }
        }
        return winner to payouts  // Return the winner and payouts map
    }

    // Register a player
    fun registerPlayer(player: Player) {
        players[player.id] = player
    }

    // Place a bet
    fun placeBet(playerId: String, horse: HorseColor, amount: Int): Boolean {
        val player = players[playerId] ?: return false
        // Logic to place a bet (check balance, etc.)
        if (player.balance >= amount) {
            player.balance -= amount  // Deduct the bet amount from the player's balance
            return true  // Bet is placed successfully
        }
        return false  // Not enough balance for the bet
    }

    // Get player details
    fun getPlayer(id: String): Player? {
        return players[id]
    }
}