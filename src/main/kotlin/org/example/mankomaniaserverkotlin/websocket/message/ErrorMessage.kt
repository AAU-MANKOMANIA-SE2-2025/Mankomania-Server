package org.example.mankomaniaserverkotlin.websocket.message

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    override val correlationId: String? = null,
    val errorCode: Int,
    val errorMessage: String,
    val details: String? = null
) : ResponseMessage()
