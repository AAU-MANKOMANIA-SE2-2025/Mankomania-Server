package org.example.mankomaniaserverkotlin

import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.example.mankomaniaserverkotlin.websocket.GameWebSocketHandler
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

/**
 * This test class verifies the behavior of the GameWebSocketHandler.
 *
 * It tests:
 * 1. That a valid JSON message (representing a move command) is correctly processed
 *    without sending an error message.
 * 2. That an invalid JSON message results in sending an error message to the client.
 *
 * This test is self-contained and does not depend on external message classes.
 */
class GameWebSocketHandlerTest {
    private lateinit var session: WebSocketSession
    private lateinit var handler: GameWebSocketHandler

    @BeforeEach
    fun setUp() {
        // Create a relaxed mock for the WebSocketSession.
        session = mockk(relaxed = true)
        // Create a spy for the handler to call real methods and verify interactions.
        handler = spyk(GameWebSocketHandler())
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should process valid JSON message without sending error`() {
        // Define a valid JSON string representing a move command.
        // The JSON must include fields like "type", "x", "y", "steps", and "correlationId".
        val validJson = """{
            "type": "move",
            "x": 1,
            "y": 2,
            "steps": 3,
            "correlationId": "test-001"
        }"""

        // Call handleTextMessage with the valid JSON message.
        handler.handleTextMessage(session, TextMessage(validJson))

        // Verify that session.sendMessage was NOT called with an error message.
        verify(exactly = 0) {
            session.sendMessage(
                match { msg ->
                    msg is TextMessage && msg.payload.contains("Error")
                },
            )
        }
    }

    @Test
    fun `should send error message for invalid JSON`() {
        // Provide an invalid JSON string that will trigger a SerializationException.
        val invalidJson = "{ invalid json }"

        // Call handleTextMessage with the invalid JSON.
        handler.handleTextMessage(session, TextMessage(invalidJson))

        // Verify that an error message is sent to the session.
        // The error message payload should include details like error code 400 and "Error processing message".
        verify {
            session.sendMessage(
                match { msg ->
                    msg is TextMessage &&
                        msg.payload.contains("400") &&
                        msg.payload.contains("Error processing message")
                },
            )
        }
    }
}
