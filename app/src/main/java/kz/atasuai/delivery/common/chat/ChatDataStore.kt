package kz.atasuai.delivery.common.chat

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SerializableChatMessage(
    val type: String,
    val content: String,
    val timestamp: Long
)

class ChatDataStore(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val CHAT_MESSAGES_KEY = stringPreferencesKey("chat_messages")
        private const val RETENTION_DAYS = 7L
        private const val MAX_MESSAGES = 100
    }
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun saveMessage(message: ChatMessage) {
        dataStore.edit { preferences ->
            val currentMessages = getCurrentMessages(preferences)
            val updatedMessages = (currentMessages + message.toSerializable())
                .takeLast(MAX_MESSAGES)
            preferences[CHAT_MESSAGES_KEY] = json.encodeToString(updatedMessages)
        }
    }

    fun getMessages(): Flow<List<ChatMessage>> {
        return dataStore.data.map { preferences ->
            getCurrentMessages(preferences)
                .filter { isMessageValid(it.timestamp) }
                .map { it.toChatMessage() }
        }
    }

    suspend fun clearExpiredMessages() {
        dataStore.edit { preferences ->
            val validMessages = getCurrentMessages(preferences)
                .filter { isMessageValid(it.timestamp) }
            preferences[CHAT_MESSAGES_KEY] = json.encodeToString(validMessages)
        }
    }

    suspend fun clearAllMessages() {
        dataStore.edit { preferences ->
            preferences.remove(CHAT_MESSAGES_KEY)
        }
    }

    private fun getCurrentMessages(preferences: Preferences): List<SerializableChatMessage> {
        return try {
            val messagesJson = preferences[CHAT_MESSAGES_KEY] ?: return emptyList()
            json.decodeFromString<List<SerializableChatMessage>>(messagesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun isMessageValid(timestamp: Long): Boolean {
        val retentionMillis = RETENTION_DAYS * 24 * 60 * 60 * 1000
        return System.currentTimeMillis() - timestamp <= retentionMillis
    }

    private fun ChatMessage.toSerializable() = SerializableChatMessage(
        type = type.name,
        content = content,
        timestamp = timestamp
    )

    private fun SerializableChatMessage.toChatMessage() = ChatMessage(
        type = MessageType.valueOf(type),
        content = content,
        timestamp = timestamp
    )
}