package kz.atasuai.delivery.common.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.atasuai.delivery.ui.viewmodels.chat.ChatBotViewModel


class ChatBotViewModelFactory(private val chatDataStore: ChatDataStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            if (modelClass.isAssignableFrom(ChatBotViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ChatBotViewModel(chatDataStore) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } catch (e: IllegalArgumentException) {
            // 记录具体的错误信息
            throw e
        } catch (e: Exception) {
            // 捕获其他可能的异常
            e.printStackTrace()
            throw IllegalStateException("Failed to create ChatBotViewModel", e)
        }
    }
}