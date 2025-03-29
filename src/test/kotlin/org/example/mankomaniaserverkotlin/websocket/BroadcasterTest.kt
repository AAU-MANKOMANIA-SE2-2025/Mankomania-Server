package org.example.mankomaniaserverkotlin.websocket

import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.Serializable
import org.example.mankomaniaserverkotlin.websocket.util.SerializationUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * A simple serializable data class used solely for testing broadcasting.
 */
@Serializable
data class TestBroadcastMessage(
    val content: String,
)

/**
 * This test class verifies the functionality of the Broadcaster object.
 *
 * It tests:
 * 1. That a broadcast message is sent to all registered sessions.
 * 2. That unregistered sessions do not receive broadcast messages.
 * 3. That broadcasting when no sessions are registered does not throw an error.
 * 4. That broadcasting continues even if one session fails.
 * 5. That concurrent broadcasts work correctly.
 * 6. That after clearing sessions, no messages are sent.
 */
class BroadcasterTest {
    @BeforeEach
    fun setUp() {
        // Ensure that the broadcaster is empty before each test.
        Broadcaster.clearSessions()
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should broadcast message to all registered sessions`() {
        val session1 = mockk<WebSocketSession>(relaxed = true)
        val session2 = mockk<WebSocketSession>(relaxed = true)

        Broadcaster.registerSession(session1)
        Broadcaster.registerSession(session2)

        val testMessage = TestBroadcastMessage("Hello, world!")
        Broadcaster.broadcast(testMessage)

        val expectedJson = SerializationUtils.serializeMessage(testMessage)

        verify { session1.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
        verify { session2.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
    }

    @Test
    fun `should not broadcast to unregistered sessions`() {
        val session1 = mockk<WebSocketSession>(relaxed = true)
        val session2 = mockk<WebSocketSession>(relaxed = true)

        Broadcaster.registerSession(session1)
        Broadcaster.registerSession(session2)

        Broadcaster.unregisterSession(session1)

        val testMessage = TestBroadcastMessage("Test Message")
        Broadcaster.broadcast(testMessage)

        val expectedJson = SerializationUtils.serializeMessage(testMessage)

        verify(exactly = 0) { session1.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
        verify { session2.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
    }

    @Test
    fun `should handle broadcasting when no sessions are registered without error`() {
        Broadcaster.clearSessions()
        val testMessage = TestBroadcastMessage("No sessions")
        // Broadcasting with an empty session set should not throw an exception.
        Broadcaster.broadcast(testMessage)
    }

    @Test
    fun `should continue broadcasting even if one session fails`() {
        // Create one session that throws an exception on sendMessage.
        val failingSession = mockk<WebSocketSession>(relaxed = false)
        every { failingSession.sendMessage(any()) } throws IOException("Simulated failure")

        // Create another session that works normally.
        val normalSession = mockk<WebSocketSession>(relaxed = true)

        Broadcaster.registerSession(failingSession)
        Broadcaster.registerSession(normalSession)

        val testMessage = TestBroadcastMessage("Message with failure")
        Broadcaster.broadcast(testMessage)
        val expectedJson = SerializationUtils.serializeMessage(testMessage)

        // Verify that the normal session received the message.
        verify { normalSession.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
        // We don't verify failingSession here because it is expected to throw,
        // but our broadcaster catches the exception.
    }

    @Test
    fun `should support concurrent broadcasts`() {
        val session1 = mockk<WebSocketSession>(relaxed = true)
        val session2 = mockk<WebSocketSession>(relaxed = true)
        val session3 = mockk<WebSocketSession>(relaxed = true)
        Broadcaster.registerSession(session1)
        Broadcaster.registerSession(session2)
        Broadcaster.registerSession(session3)

        val executor = Executors.newFixedThreadPool(3)
        val messages =
            listOf(
                TestBroadcastMessage("Message 1"),
                TestBroadcastMessage("Message 2"),
                TestBroadcastMessage("Message 3"),
            )

        messages.forEach { msg ->
            executor.submit { Broadcaster.broadcast(msg) }
        }
        executor.shutdown()
        executor.awaitTermination(5, TimeUnit.SECONDS)

        messages.forEach { msg ->
            val expectedJson = SerializationUtils.serializeMessage(msg)
            verify { session1.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
            verify { session2.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
            verify { session3.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
        }
    }

    @Test
    fun `should not broadcast after clearSessions is called`() {
        val session = mockk<WebSocketSession>(relaxed = true)
        Broadcaster.registerSession(session)
        Broadcaster.clearSessions()
        val testMessage = TestBroadcastMessage("No recipients")
        Broadcaster.broadcast(testMessage)
        val expectedJson = SerializationUtils.serializeMessage(testMessage)
        verify(exactly = 0) { session.sendMessage(match { it is TextMessage && it.payload == expectedJson }) }
    }
}
