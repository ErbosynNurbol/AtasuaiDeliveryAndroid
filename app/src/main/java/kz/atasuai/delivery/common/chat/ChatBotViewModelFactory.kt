package kz.atasuai.delivery.common.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.atasuai.delivery.ui.viewmodels.chat.ChatBotViewModel


class ChatBotViewModelFactory(private val chatDataStore: ChatDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatBotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatBotViewModel(chatDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}