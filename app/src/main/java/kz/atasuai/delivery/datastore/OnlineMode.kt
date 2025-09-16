package kz.atasuai.delivery.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "online_mode_settings")

object OnlineMode {
    private var dataStore: DataStore<Preferences>? = null

    private val ONLINE_KEY = booleanPreferencesKey("is_online")

    private val _isOnline = MutableStateFlow(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    fun initialize(context: Context) {
        if (dataStore != null) return

        dataStore = context.dataStore

        CoroutineScope(Dispatchers.IO).launch {
            try {
                dataStore?.data?.collect { preferences ->
                    val savedValue = preferences[ONLINE_KEY] ?: true
                    _isOnline.value = savedValue
                }
            } catch (e: Exception) {
                // 如果读取失败，使用默认值
                _isOnline.value = true
            }
        }
    }

    fun setOnline(value: Boolean) {
        _isOnline.value = value
        CoroutineScope(Dispatchers.IO).launch {
            try {
                dataStore?.edit { preferences ->
                    preferences[ONLINE_KEY] = value
                }
            } catch (e: Exception) {

            }
        }
    }
}