package org.example.mankomaniaserverkotlin.websocket.dispatcher

import org.springframework.web.socket.WebSocketSession
import org.example.mankomaniaserverkotlin.websocket.message.GameMessage
import org.example.mankomaniaserverkotlin.websocket.handler.BaseCommandHandler
import org.example.mankomaniaserverkotlin.websocket.handler.MoveCommandHandler

class MessageDispatcher {
    private val handlers: MutableMap<String, BaseCommandHandler> = mutableMapOf()

    init {
        registerHandler("move", MoveCommandHandler())
    }

    fun registerHandler(type: String, handler: BaseCommandHandler) {
        handlers[type] = handler
    }

    fun dispatch(message: GameMessage, session: WebSocketSession) {
        val handler = handlers[message.type]
        if (handler != null) {
            try {
                handler.handle(message, session)
            } catch (e: Exception) {
                // Catch and log the exception instead of propagating it.
                println("Exception [ERROR] in handler for message type '${message.type}': ${e.message}")
            }
        } else {
            println("No handler [ERROR] for message type: ${message.type}")
        }
    }
}
