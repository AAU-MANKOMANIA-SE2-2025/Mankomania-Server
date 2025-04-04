package org.example.mankomaniaserverkotlin.websocket

import org.example.mankomaniaserverkotlin.websocket.util.SerializationUtils
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

/**
 * GameWebSocketHandler is responsible for handling incoming WebSocket messages.
 *
 * It processes the incoming JSON message:
 * - If the JSON is valid, it simulates processing the message.
 * - If the JSON is invalid, it catches the error and sends an error response to the client.
 *
 * Note: This implementation is self-contained and does not depend on external message classes.
 */
class GameWebSocketHandler : TextWebSocketHandler() {
    private val maxPlayers = 1
    private val broadcastResponseDemonstrationDemo = Broadcaster.broadcast("response:Server says hi to all clients!")

    // This method is now explicitly public so it can be called by tests.
    public override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage,
    ) {
        val payload = message.payload
        try {
            // Verify JSON validity.
            SerializationUtils.json.parseToJsonElement(payload)
            println("Processed valid message: $payload")
        } catch (e: Exception) {
            val errorJson = """{"errorCode":400,"errorMessage":"Error processing message","details":"$payload"}"""
            session.sendMessage(TextMessage(errorJson))
            println("Sent error response: $errorJson")
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        if (WebSocketManager.getActiveSessionCount() >= maxPlayers) {
            session.close(CloseStatus(4001, "Room is full"))
            println("Verbindung abgelehnt: Raum voll (Session ${session.id})")
            return
        }
        Broadcaster.registerSession(session)
        println("Session registered: ${session.id}")
    }
}
