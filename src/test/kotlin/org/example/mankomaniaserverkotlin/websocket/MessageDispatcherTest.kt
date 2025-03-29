package org.example.mankomaniaserverkotlin.websocket

import io.mockk.clearAllMocks
import io.mockk.mockk
import org.example.mankomaniaserverkotlin.websocket.dispatcher.MessageDispatcher
import org.example.mankomaniaserverkotlin.websocket.handler.BaseCommandHandler
import org.example.mankomaniaserverkotlin.websocket.message.GameMessage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.socket.WebSocketSession

/**
 * This test ensures that the MessageDispatcher:
 *  - Correctly dispatches incoming messages to the registered handler based on their type.
 *  - Does not throw an exception when no handler is registered for a message.
 *  - Logs any exception thrown by a handler.
 */
class MessageDispatcherTest {
    private lateinit var dispatcher: MessageDispatcher
    private lateinit var mockSession: WebSocketSession
    private lateinit var dummyHandler: DummyHandler

    @BeforeEach
    fun setUp() {
        // Initialize the dispatcher and a dummy WebSocketSession mock.
        dispatcher = MessageDispatcher()
        mockSession = mockk(relaxed = true)

        // Create a dummy handler that records if it has been called.
        dummyHandler = DummyHandler()

        // Register the dummy handler for message type "test".
        dispatcher.registerHandler("test", dummyHandler)
    }

    @AfterEach
    fun tearDown() {
        // Clear all mocks after each test.
        clearAllMocks()
    }

    @Test
    fun `should dispatch message to registered handler`() {
        // Create a test message of type "test" with a correlation ID.
        val testMessage = object : GameMessage("test", "corr-002") {}

        // Dispatch the message.
        dispatcher.dispatch(testMessage, mockSession)

        // Verify that the dummy handler was called.
        assertTrue(dummyHandler.wasCalled, "Handler should have been called.")
        // Verify that the received message matches the sent message.
        assertEquals(testMessage, dummyHandler.receivedMessage, "Message must be passed correctly.")
        // Verify that the received WebSocketSession is correct.
        assertEquals(mockSession, dummyHandler.receivedSession, "Session must be passed correctly.")
    }

    @Test
    fun `should not dispatch message if no handler registered`() {
        // Create a message with an unknown type.
        val unknownMessage = object : GameMessage("unknown", "corr-003") {}

        // Expect that no exception is thrown when no handler is registered.
        dispatcher.dispatch(unknownMessage, mockSession)

        // There is no additional state to verify except that no exception occurred.
    }

    @Test
    fun `should log exception when handler throws error`() {
        // Arrange: Create a handler that intentionally throws an exception.
        val throwingHandler =
            object : BaseCommandHandler {
                override fun handle(
                    message: GameMessage,
                    session: WebSocketSession,
                ): Unit = throw RuntimeException("Simulated handler error")
            }
        // Register the throwing handler under a specific message type.
        dispatcher.registerHandler("errorTest", throwingHandler)
        val errorTestMessage = object : GameMessage("errorTest", "corr-004") {}

        // Capture System.out to test the log output.
        val outputStream = java.io.ByteArrayOutputStream()
        val originalOut = System.out
        System.setOut(java.io.PrintStream(outputStream))

        try {
            // Act: Dispatch the message that triggers the exception in the handler.
            dispatcher.dispatch(errorTestMessage, mockSession)
        } finally {
            // Ensure System.out is restored.
            System.setOut(originalOut)
        }

        // Assert: Verify that the log output contains the expected exception message.
        val logOutput = outputStream.toString()
        assertTrue(
            logOutput.contains("Simulated handler error"),
            "Expected log output to contain the simulated error message",
        )
    }

    /**
     * DummyHandler implements BaseCommandHandler and records whether it was called,
     * as well as the received parameters, for verification in tests.
     */
    class DummyHandler : BaseCommandHandler {
        var wasCalled: Boolean = false
        var receivedMessage: GameMessage? = null
        var receivedSession: WebSocketSession? = null

        override fun handle(
            message: GameMessage,
            session: WebSocketSession,
        ) {
            wasCalled = true
            receivedMessage = message
            receivedSession = session
        }
    }
}
