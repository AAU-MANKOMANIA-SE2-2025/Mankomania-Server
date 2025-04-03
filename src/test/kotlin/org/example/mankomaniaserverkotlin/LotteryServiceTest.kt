package org.example.mankomaniaserverkotlin.lottery

import org.example.mankomaniaserverkotlin.service.LotteryService
import org.example.mankomaniaserverkotlin.model.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.*

class LotteryServiceTest {
    private lateinit var lotteryService: LotteryService
    private lateinit var richPlayer: Player
    private lateinit var poorPlayer: Player
    private lateinit var bankruptPlayer: Player

    @BeforeEach
    fun setup() {
        lotteryService = LotteryService()
        richPlayer = Player("rich", 100000)
        poorPlayer = Player("poor", 4000)
        bankruptPlayer = Player("bankrupt", 0)
    }

    // Payment when using “go to” fields
    @Test
    fun processGoToField() {
        assertTrue(lotteryService.processGoToField(richPlayer))
        assertEquals(95000, richPlayer.balance)
        assertEquals(5000, lotteryService.getPoolAmount())
    }

    // Payment when crossing the lottery field
    @Test
    fun processPassingLottery() {
        assertTrue(lotteryService.processPassingLottery(richPlayer))
        assertEquals(95000, richPlayer.balance)
        assertEquals(5000, lotteryService.getPoolAmount())
    }

    // Correct payout when landing directly on the field
    @Test
    fun landingOnLotteryWithMoney() {
        lotteryService.processGoToField(richPlayer)
        val result = lotteryService.processLandingOnLottery(richPlayer)
        assertTrue(result.success)
        assertEquals(100000, richPlayer.balance)
    }

    // Pool update logic and edge cases (e.g. empty pool)
    @Test
    fun landingOnEmptyPool() {
        val result = lotteryService.processLandingOnLottery(richPlayer)
        assertTrue(result.success)
        assertEquals(50000, richPlayer.balance)
        assertEquals(50000, lotteryService.getPoolAmount())
    }

    // Tests verify that the player never receives money more than once per landing event
    @Test
    fun playerCantReceiveMoneyTwice() {
        lotteryService.processGoToField(richPlayer)
        lotteryService.processLandingOnLottery(richPlayer)
        val result = lotteryService.processLandingOnLottery(richPlayer)
        assertTrue(result.success)
        assertEquals(50000, richPlayer.balance)
    }

    // Test if the winner is chosen correctly
    @Test
    fun playerBecomesWinnerWithNoMoney() {
        val player = Player("almostBankrupt", 5000)
        assertTrue(lotteryService.processGoToField(player))
        assertEquals(0, player.balance)
        assertFalse(lotteryService.processPassingLottery(player))
    }
}