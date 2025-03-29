package org.example.mankomaniaserverkotlin.websocket

import org.example.mankomaniaserverkotlin.websocket.util.SerializationUtils
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
    // This method is now explicitly public so it can be called by tests.
    public override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage,
    ) {
        val payload = message.payload
        try {
            // Attempt to parse the payload into a JSON element to verify its validity.
            SerializationUtils.json.parseToJsonElement(payload)
            // If parsing is successful, simulate processing the message.
            println("Processed valid message: $payload")
            // (In a real application, you might dispatch the message to appropriate handlers.)
        } catch (e: Exception) {
            // If an exception occurs, assume the JSON is invalid.
            val errorJson = """{"errorCode":400,"errorMessage":"Error processing message","details":"$payload"}"""
            session.sendMessage(TextMessage(errorJson))
            println("Sent error response: $errorJson")
        }
    }
}
