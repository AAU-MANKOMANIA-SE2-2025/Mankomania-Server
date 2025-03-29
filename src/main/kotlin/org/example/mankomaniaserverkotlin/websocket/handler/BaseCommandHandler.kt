package org.example.mankomaniaserverkotlin.websocket.handler

import org.example.mankomaniaserverkotlin.websocket.message.GameMessage
import org.springframework.web.socket.WebSocketSession

fun interface BaseCommandHandler {
    fun handle(
        message: GameMessage,
        session: WebSocketSession,
    )
}
