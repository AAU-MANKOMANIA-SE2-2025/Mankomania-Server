package org.example.mankomaniaserverkotlin.websocket

import io.mockk.mockk
import io.mockk.verify
import org.example.mankomaniaserverkotlin.websocket.config.WebSocketConfig
import org.junit.jupiter.api.Test
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

class WebSocketConfigTest {
    @Test
    fun `should register GameWebSocketHandler`() {
        // Create a relaxed mock for the WebSocketHandlerRegistry.
        val registry = mockk<WebSocketHandlerRegistry>(relaxed = true)
        // Instantiate the config.
        val config = WebSocketConfig()
        // Call the registration method.
        config.registerWebSocketHandlers(registry)
        // Verify that addHandler was called with a GameWebSocketHandler and endpoint "/ws".
        verify { registry.addHandler(match { it is GameWebSocketHandler }, "/ws") }
        // Optionally, verify that allowed origins are set.
        // (If your registry mock supports chained calls, you could verify .setAllowedOrigins("*") is called.)
    }
}
