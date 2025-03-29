package org.example.mankomaniaserverkotlin.websocket.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

/**
 * A simple data class used solely for testing serialization.
 */
@Serializable
data class TestData(
    val id: Int,
    val text: String,
)

/**
 * This test class verifies the functionality of the SerializationUtils object.
 *
 * It tests two scenarios:
 * 1. That a valid TestData instance is correctly serialized to JSON and then deserialized
 *    back to an equivalent object.
 * 2. That deserialization of an invalid JSON string throws a SerializationException.
 */
class SerializationUtilsTest {
    @Test
    fun `serialize and deserialize TestData successfully`() {
        // Arrange: Create a valid TestData instance.
        val originalData = TestData(id = 123, text = "Hello, world!")
        // Act: Serialize the object to JSON.
        val jsonString = SerializationUtils.serializeMessage(originalData)
        // Then deserialize the JSON back into a TestData instance.
        val deserializedData = SerializationUtils.deserializeMessage<TestData>(jsonString)
        // Assert: The original and deserialized objects should be equal.
        assertEquals(originalData, deserializedData, "Deserialized object should match the original")
    }

    @Test
    fun `deserialization of invalid JSON should throw SerializationException`() {
        // Arrange: Define an invalid JSON string.
        val invalidJson = "{ invalid json }"
        // Act & Assert: Expect a SerializationException when trying to deserialize the invalid JSON.
        assertThrows(SerializationException::class.java) {
            SerializationUtils.deserializeMessage<TestData>(invalidJson)
        }
    }
}
