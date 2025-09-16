package kz.atasuai.delivery.ui.viewmodels.chat

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.common.chat.ChatDataStore
import kz.atasuai.delivery.common.chat.ChatMessage
import kz.atasuai.delivery.common.chat.MessageType

class ChatBotViewModel(
    private val chatDataStore: ChatDataStore
) : QarBaseViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()


    init {
        loadMessages()
        clearExpiredMessages()
    }

    fun updateInputText(text: String) {
        _inputText.value = text
    }

    fun sendMessage() {
        val message = _inputText.value.trim()
        if (message.isEmpty()) return

        viewModelScope.launch {
            val userMessage = ChatMessage(MessageType.USER, message)
            chatDataStore.saveMessage(userMessage)
            _inputText.value = ""

            _isLoading.value = true

            val botResponse = generateBotResponse(message)
            val botMessage = ChatMessage(MessageType.BOT, botResponse)
            chatDataStore.saveMessage(botMessage)

            _isLoading.value = false
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            chatDataStore.clearAllMessages()
        }
    }

    private fun loadMessages() {
        viewModelScope.launch {
            chatDataStore.getMessages().collect { messageList ->
                _messages.value = messageList
            }
        }
    }

    private fun clearExpiredMessages() {
        viewModelScope.launch {
            chatDataStore.clearExpiredMessages()
        }
    }

    private suspend fun generateBotResponse(userMessage: String): String {
        return when {
            userMessage.contains("бонус", ignoreCase = true) ->
                "Бонустарыңызды 'Менің аккаунтым' бөлімінен көре аласыз."
            userMessage.contains("көмек", ignoreCase = true) ->
                "Мен сізге көмектесуге дайынмын! Сұрағыңызды нақтырақ айтыңыз."
            else ->
                "Сіздің сұрағыңыз түсінікті емес. Қайта сұрап көріңіз."
        }
    }


    fun messageToBac(userMessage: String,onComplete: (Boolean) -> Unit = {}){
        val paraDic = mapOf(
            "user" to userMessage
        )
        viewModelScope.launch {
            val url = "/bonus/history"
            val result = APIHelper.queryAsync(url, "POST", paraDic = paraDic)
            result.fold(
                onSuccess = { ajaxMsg ->
                    if (ajaxMsg.status.equals("success", ignoreCase = true)) {

                        onComplete(true)
                    } else {
                        onComplete(false)
                    }

                },
                onFailure = { exception ->

                    onComplete(false)
                }
            )
        }
    }
}