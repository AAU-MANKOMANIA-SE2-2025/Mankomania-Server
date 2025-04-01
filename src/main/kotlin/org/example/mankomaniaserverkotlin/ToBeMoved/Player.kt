package com.example.mankomania_client.model

data class Player(
    val name: String,
    var position: Int = 0,
    var money: Int = 0,
    val maxFields: Int = 40// placeholder
    ) {

    fun move(steps:Int){
        position= (position + steps) % maxFields //if on 40 --> wrap around back to 1
    }


}