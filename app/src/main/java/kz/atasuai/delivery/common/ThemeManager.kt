package kz.atasuai.delivery.common

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(loadThemeMode())
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private fun loadThemeMode(): ThemeMode {
        val ordinal = sharedPreferences.getInt("theme_mode", ThemeMode.FOLLOW_SYSTEM.ordinal)
        return ThemeMode.values().getOrNull(ordinal) ?: ThemeMode.FOLLOW_SYSTEM
    }

    fun updateThemeMode(mode: ThemeMode) {
        sharedPreferences.edit().putInt("theme_mode", mode.ordinal).apply()
        _themeMode.value = mode

        val nightMode = when (mode) {
            ThemeMode.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            ThemeMode.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
            ThemeMode.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }
}
