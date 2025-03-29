package org.example.mankomaniaserverkotlin.websocket.message

import kotlinx.serialization.Serializable

@Serializable
sealed class ResponseMessage {
    abstract val correlationId: String?
}
