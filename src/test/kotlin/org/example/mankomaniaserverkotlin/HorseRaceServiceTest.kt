package org.example.mankomaniaserverkotlin.controller

import org.example.mankomaniaserverkotlin.model.HorseColor
import org.example.mankomaniaserverkotlin.model.Player
import org.example.mankomaniaserverkotlin.service.HorseRaceService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class HorseRaceServiceTest{

    @Autowired
    private lateinit var horseRaceService: HorseRaceService

    @Test
    fun `test spin roulette`() {
        val result = horseRaceService.spinRoulette()

        Assertions.assertNotNull(result)
    }

    @Test
    fun `test start race`() {
        val result = horseRaceService.startRace(listOf(), mapOf())

        Assertions.assertNotNull(result)
    }

    @Test
    fun `test register player`() {
        val balance = 2025
        horseRaceService.registerPlayer(player = Player("test-player", balance))
        val player = horseRaceService.getPlayer("test-player")

        Assertions.assertNotNull(player)
        Assertions.assertEquals(player!!.balance, balance)
    }

    @Test
    fun `test place bet - player does not exist`() {
        val result = horseRaceService.placeBet("test-player", HorseColor.BLUE, 2025)

        Assertions.assertFalse(result)
    }

    @Test
    fun `test place bet - not enough balance`() {
        horseRaceService.registerPlayer(player = Player("test-player", 0))
        val result = horseRaceService.placeBet("test-player", HorseColor.BLUE, 2025)

        Assertions.assertFalse(result)
    }

    @Test
    fun `test place bet`() {
        horseRaceService.registerPlayer(player = Player("test-player", 2025))
        val result = horseRaceService.placeBet("test-player", HorseColor.BLUE, 2025)

        Assertions.assertTrue(result)
    }
}