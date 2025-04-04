package org.example.mankomaniaserverkotlin.websocket

import org.springframework.web.socket.WebSocketSession

/**
 * WebSocketManager manages active WebSocket sessions.
 *
 * It maintains a mapping from session ID to WebSocketSession.
 */
object WebSocketManager {
    private val sessions = mutableMapOf<String, WebSocketSession>()

    fun addSession(session: WebSocketSession) {
        sessions[session.id] = session
    }

    fun removeSession(session: WebSocketSession) {
        sessions.remove(session.id)
    }

    fun getSession(id: String): WebSocketSession? = sessions[id]

    fun clearSessions() {
        sessions.clear()
    }

    fun getActiveSessionCount(): Int = Broadcaster.sessions.size
}
