package org.example.mankomaniaserverkotlin.websocket.message

import kotlinx.serialization.Serializable

@Serializable
data class AckMessage(
    override val correlationId: String? = null,
    val status: String = "success",
    val message: String? = null,
) : ResponseMessage()
