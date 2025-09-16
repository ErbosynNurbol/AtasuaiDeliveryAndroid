package kz.atasuai.delivery.common.chat

data class ChatMessage(
    val type: MessageType,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageType {
    USER, BOT
}
