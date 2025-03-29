package org.example.mankomaniaserverkotlin.websocket.config

import org.example.mankomaniaserverkotlin.websocket.GameWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(GameWebSocketHandler(), "/ws")
            .setAllowedOrigins("*")
    }
}
