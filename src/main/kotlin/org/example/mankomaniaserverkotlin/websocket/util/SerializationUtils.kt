package org.example.mankomaniaserverkotlin.websocket.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SerializationUtils {
    val json = Json { ignoreUnknownKeys = true }

    inline fun <reified T> deserializeMessage(jsonString: String): T = json.decodeFromString(jsonString)

    inline fun <reified T> serializeMessage(message: T): String = json.encodeToString(message)
}
