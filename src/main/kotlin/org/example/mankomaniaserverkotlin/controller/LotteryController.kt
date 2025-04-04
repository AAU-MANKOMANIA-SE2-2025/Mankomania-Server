package org.example.mankomaniaserverkotlin.lottery

import org.example.mankomaniaserverkotlin.service.LotteryService
import org.example.mankomaniaserverkotlin.model.Player
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lottery")
class LotteryController(private val lotteryService: LotteryService) {

    @GetMapping("/pool")
    fun getPoolAmount(): Map<String, Int> {
        return mapOf("amount" to lotteryService.getPoolAmount())
    }

    @PostMapping("/payment/{playerId}")
    fun processPayment(
        @PathVariable playerId: String,
        @RequestBody player: Player,
        @RequestParam reason: String
    ): Map<String, Any> {
        val success = when (reason) {
            "goToField" -> lotteryService.processGoToField(player)
            "passing" -> lotteryService.processPassingLottery(player)
            else -> false
        }

        return mapOf(
            "success" to success,
            "poolAmount" to lotteryService.getPoolAmount(),
            "playerBalance" to player.balance
        )
    }

    @PostMapping("/land")
    fun processLanding(@RequestBody player: Player): Map<String, Any> {
        val result = lotteryService.processLandingOnLottery(player)
        return mapOf(
            "success" to result.success,
            "message" to result.message,
            "poolAmount" to lotteryService.getPoolAmount(),
            "playerBalance" to player.balance
        )
    }

    @GetMapping("/current")
    fun getCurrentLotteryAmount(): ResponseEntity<Int> {
        return ResponseEntity.ok(lotteryService.getCurrentLotteryAmount())
    }

    @PostMapping("/pay-with-notification")
    fun processPaymentWithNotification(
        @RequestBody player: Player,
        @RequestParam reason: String
    ): ResponseEntity<Map<String, Any>> {
        val (success, message) = lotteryService.processPaymentWithNotification(player, 5000, reason)
        return ResponseEntity.ok(mapOf(
            "success" to success,
            "message" to message,
            "newPoolAmount" to lotteryService.getPoolAmount()
        ))
    }

}