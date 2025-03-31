package com.example.mankomania_client.model

data class Player(
    val name: String,
    var position: Int = 0,
    var money: Int = 0) {

    fun move(steps:Int){
        position += steps
    }


}