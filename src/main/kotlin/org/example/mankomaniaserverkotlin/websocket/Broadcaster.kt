package org.example.mankomaniaserverkotlin.websocket

import org.example.mankomaniaserverkotlin.websocket.util.SerializationUtils
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

/**
 * Broadcaster maintains a set of WebSocket sessions and broadcasts a given message
 * (serialized to JSON) to all registered sessions.
 */
object Broadcaster {
    val sessions = mutableSetOf<WebSocketSession>()

    fun registerSession(session: WebSocketSession) {
        sessions.add(session)
    }

    fun unregisterSession(session: WebSocketSession) {
        sessions.remove(session)
    }

    /**
     * Broadcasts the message to all registered sessions.
     * This function is inline and generic, so only serializable types can be broadcast.
     * If a session fails when sending a message, it catches the exception and continues.
     */
    inline fun <reified T> broadcast(message: T) {
        val json = SerializationUtils.serializeMessage(message)
        sessions.forEach { session ->
            try {
                session.sendMessage(TextMessage(json))
            } catch (e: Exception) {
                println("Error sending message to session: ${e.message}")
            }
        }
        println("Broadcast sent: $json")
    }

    // Helper function for tests to clear all sessions.
    fun clearSessions() {
        sessions.clear()
    }
}
