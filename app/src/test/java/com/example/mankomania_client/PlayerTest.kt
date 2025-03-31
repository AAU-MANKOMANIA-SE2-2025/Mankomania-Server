package com.example.mankomania_client

import com.example.mankomania_client.model.Player
import org.junit.Test
import org.junit.Assert.assertEquals


class PlayerTest {

    @Test
    fun playerMovesCorrectlyBasedOnDiceRoll (){
        val player = Player(name = "blue", position = 0)
        player.move(5)
        assertEquals(5,player.position)

        player.move(3)
        assertEquals(8,player.position)
    }

    @Test
    fun playerWrapsAroundBoardCorrectly (){
        val player = Player(name = "blue", position = 30)
        player.move(5)
        assertEquals(3,player.position)
    }

    @Test
    fun playerLandsExactlyOnLastField (){
        val player = Player(name = "blue", position = 28)
        player.move(4)
        assertEquals(0,player.position) //32 % 32 = 0...
    }
}