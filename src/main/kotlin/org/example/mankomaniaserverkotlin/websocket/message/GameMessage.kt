package org.example.mankomaniaserverkotlin.websocket.message

import kotlinx.serialization.Serializable

@Serializable
abstract class GameMessage(
    val type: String,
    open val correlationId: String? = null,
)
