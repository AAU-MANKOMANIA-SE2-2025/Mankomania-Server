package org.example.mankomaniaserverkotlin.model

data class Bet(
    val playerId: String,
    val horse: HorseColor,
    val amount: Int
)