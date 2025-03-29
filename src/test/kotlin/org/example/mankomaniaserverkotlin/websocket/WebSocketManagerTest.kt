package org.example.mankomaniaserverkotlin.websocket

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.socket.WebSocketSession

class WebSocketManagerTest {
    @BeforeEach
    fun setUp() {
        WebSocketManager.clearSessions()
    }

    @AfterEach
    fun tearDown() {
        WebSocketManager.clearSessions()
    }

    @Test
    fun `should add and retrieve session`() {
        // Create a mock session with a predetermined id.
        val session =
            mockk<WebSocketSession>(relaxed = true) {
                every { id } returns "session-123"
            }
        WebSocketManager.addSession(session)
        val retrieved = WebSocketManager.getSession("session-123")
        assertEquals("session-123", retrieved?.id)
    }

    @Test
    fun `should remove session`() {
        val session =
            mockk<WebSocketSession>(relaxed = true) {
                every { id } returns "session-456"
            }
        WebSocketManager.addSession(session)
        WebSocketManager.removeSession(session)
        val retrieved = WebSocketManager.getSession("session-456")
        assertNull(retrieved)
    }

    @Test
    fun `should clear all sessions`() {
        val session1 =
            mockk<WebSocketSession>(relaxed = true) {
                every { id } returns "s1"
            }
        val session2 =
            mockk<WebSocketSession>(relaxed = true) {
                every { id } returns "s2"
            }
        WebSocketManager.addSession(session1)
        WebSocketManager.addSession(session2)
        WebSocketManager.clearSessions()
        assertNull(WebSocketManager.getSession("s1"))
        assertNull(WebSocketManager.getSession("s2"))
    }
}
