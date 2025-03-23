/**
 * @file CellAction.kt
 * @author 
 * @since 
 * @description Interface für Zellenaktionen.
 */
package org.example.mankomaniaserverkotlin.model

interface CellAction {
    fun execute(player: Player, gameController: org.example.mankomaniaserverkotlin.controller.GameController)
}