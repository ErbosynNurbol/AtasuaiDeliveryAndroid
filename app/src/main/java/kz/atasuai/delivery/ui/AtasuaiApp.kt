package kz.atasuai.delivery.ui

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Process
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.atasuai.delivery.common.JsonHelper
import kz.atasuai.delivery.common.ThemeManager
import kz.atasuai.delivery.common.TokenHelper
import kz.atasuai.delivery.common.Translator
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.models.global.OrderUiState
import kz.atasuai.market.models.global.PersonModel
import java.util.Locale
import kotlin.system.exitProcess

class AtasuaiApp: Application(){
    companion object {
        private const val PREFS_NAME = "AtasuaiApp"
        private const val FIRST_LAUNCH_KEY = "IsFirstLaunch"
        private const val CURRENT_LANGUAGE_KEY = "CurrentLanguage"
        private const val QAR_TOKEN_KEY = "QarToken"
        private const val LAST_SMS_TIMESTAMP_PREFIX = "LastSMSTimestamp_"
        private const val CITY_LOCAL_KEY = "NeGoiNeJoh"
        private const val EXPIRATION_TIME_SECONDS = 120 // 2 minutes
        lateinit var themeManager: ThemeManager
            private set
        private lateinit var prefs: SharedPreferences
        val defaultLanguage: LanguageModel by lazy {
            val locale = Locale.getDefault()
            when (locale.language) {
                "kk" -> LanguageModel(
                    flagUrl = "https://aljet.kz/images/flag/kz.svg",
                    fullName = "Қазақша",
                    shortName = "Қаз",
                    culture = "kz",
                    isDefault = true,
                    uniqueSeoCode = "kk"
                )
                "ru" -> LanguageModel(
                    flagUrl = "https://aljet.kz/images/flag/ru.svg",
                    fullName = "Русский",
                    shortName = "Рус",
                    culture = "ru",
                    isDefault = true,
                    uniqueSeoCode = "ru"
                )
                "zh" -> LanguageModel(
                    flagUrl = "https://aljet.kz/images/flag/cn.svg",
                    fullName = "简体中文",
                    shortName = "中",
                    culture = "zh-CN",
                    isDefault = true,
                    uniqueSeoCode = "zh"
                )
                "en" -> LanguageModel(
                    flagUrl = "https://aljet.kz/images/flag/en.svg",
                    fullName = "English",
                    shortName = "En",
                    culture = "en",
                    isDefault = true,
                    uniqueSeoCode = "en"
                )
                else -> LanguageModel(
                    flagUrl = "https://aljet.kz/images/flag/en.svg",
                    fullName = "English",
                    shortName = "En",
                    culture = "en",
                    isDefault = true,
                    uniqueSeoCode = "en"
                )
            }
        }
        private val _currentLanguage = MutableStateFlow(defaultLanguage)
        val currentLanguage: StateFlow<LanguageModel> = _currentLanguage.asStateFlow()

        private val _isFirstRegis = MutableStateFlow<Boolean>(false)
        val isFirstRegis: StateFlow<Boolean> = _isFirstRegis.asStateFlow()
        fun updateIsFirstRegis(value: Boolean) {
            _isFirstRegis.value = value
        }

        var currentPerson: PersonModel? = null
            private set
//        eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJQZXJzb25JZCI6IjkiLCJTdG9yZUlkIjoiMSIsIk5hbWUiOiIiLCJTdXJOYW1lIjoiIiwiQXZhdGFyVXJsIjoiaHR0cHM6Ly9wb3MuMzEwMC5rei9pbWFnZXMvZGVmYXVsdF9hdmF0YXIucG5nIiwiUGhvbmUiOiIrNzc3MTMzNDUyNTUiLCJIYXNQYXNzd29yZCI6IkZhbHNlIiwiQmlydGhkYXkiOiIiLCJuYmYiOjE3NTM3OTI0MjUsImV4cCI6MTc4NTMyODQyNSwiaWF0IjoxNzUzNzkyNDI1LCJpc3MiOiJzaG9wLnFhci5reiIsImF1ZCI6InNob3AucWFyLmt6In0.G3Ij-Sz89bNRC9XLbokAoNDPAtzT_iKkbMCq6MRxGpE
        var currentToken: String = ""
            private set
        var currentJsn: String = ""
            private set
        var currentAndroidToken: String = ""
            private set
        var deliveryRange:Int = 100
            private set

        lateinit var appContext: Context
            private set

        lateinit var siteUrl: String
            private set

        var isFirstLaunch: Boolean = false
            private set


        var facebookUrl by mutableStateOf("")
        var instagramUrl by mutableStateOf("")
        var telegramUrl by mutableStateOf("")
        var tiktokUrl by mutableStateOf("")
        var whatsapp by mutableStateOf("")
        var youtube by mutableStateOf("")
        fun updateLanguage(value: LanguageModel) {
            if (_currentLanguage.value != value) {
                _currentLanguage.value = value
                putCurrentLanguagePreference(JsonHelper.serializeObject(value))
            }
        }
        fun setCurrentPerson(token: String) {
            currentToken = token.ifEmpty {
                ""
            }
            currentPerson = if (token.isNotEmpty()) {
                TokenHelper.decode(token)
            } else {
                null
            }
            prefs.edit().putString(QAR_TOKEN_KEY, token).apply()
        }
        fun setCurrentJsn(jsn: String) {
            currentJsn = jsn
            prefs.edit().putString("Jsn", jsn).apply()
        }
        fun setCurrentAndroidToken(token: String) {
            currentAndroidToken = token
            prefs.edit().putString("FCM_TOKEN", token).apply()
        }

        private fun putCurrentLanguagePreference(value: String?) {
            prefs.edit().putString(CURRENT_LANGUAGE_KEY, value).apply()
        }
        private fun putCurrentCity(value: String?) {
            prefs.edit().putString(CITY_LOCAL_KEY, value).apply()
        }

        private inline fun <reified T> getPreference(key: String, defaultValue: T? = null): T? {
            return when (T::class) {
                String::class -> prefs.getString(key, defaultValue as? String) as T?
                Boolean::class -> prefs.getBoolean(key, defaultValue as? Boolean ?: false) as T?
                Int::class -> prefs.getInt(key, defaultValue as? Int ?: -1) as T?
                Long::class -> prefs.getLong(key, defaultValue as? Long ?: -1L) as T?
                Float::class -> prefs.getFloat(key, defaultValue as? Float ?: -1f) as T?
                else -> null
            }
        }
        fun saveLastSendSMSTimestamp(phoneNumber: String, timestamp: Long) {
            val key = LAST_SMS_TIMESTAMP_PREFIX + phoneNumber
            prefs.edit().putLong(key, timestamp).apply()
        }

        fun getLastSendSMSTimestamp(phoneNumber: String): Long? {
            val key = LAST_SMS_TIMESTAMP_PREFIX + phoneNumber
            val timestamp = prefs.getLong(key, 0)

            return if (timestamp != 0L && (System.currentTimeMillis() / 1000 - timestamp) <= EXPIRATION_TIME_SECONDS) {
                timestamp
            } else {
                prefs.edit().remove(key).apply() // Remove expired timestamp
                null
            }
        }

        const val resendSmsTimeSeconds = 120 // 1 minute
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        siteUrl = "https://pos.3100.kz/api"
        themeManager = ThemeManager(this)
        initializeLanguage()
        loadTranslator()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            handleUncaughtException(thread, throwable)
        }
        isFirstLaunch = getPreference(FIRST_LAUNCH_KEY, true) ?: true
        if (isFirstLaunch) {
            prefs.edit().putBoolean(FIRST_LAUNCH_KEY, false).apply()
        }

        initializeCurrentPerson()
    }

    private fun loadTranslator() {
        Translator.loadLanguagePack(_currentLanguage.value) { success ->
            if (success) {
                registerServices()
            }
        }
    }

    private fun initializeLanguage() {
        val currentLanguageJsonStr = getPreference<String>(CURRENT_LANGUAGE_KEY)
        _currentLanguage.value = if (!currentLanguageJsonStr.isNullOrEmpty()) {
            JsonHelper.deserializeObject<LanguageModel?>(currentLanguageJsonStr) ?: defaultLanguage
        } else {
            defaultLanguage
        }
    }
    fun initializeCurrentPerson() {
        val qarToken = getPreference<String>(QAR_TOKEN_KEY, "") ?: ""
        if (qarToken.isNotEmpty()) {
            setCurrentPerson(qarToken);
        }
        setCurrentJsn(getPreference<String>("Jsn", "") ?: "")
    }


    private fun handleUncaughtException(thread: Thread, throwable: Throwable) {
        throwable.printStackTrace()
        Process.killProcess(Process.myPid())
        exitProcess(10)
    }


    fun registerServices() {
        val hasLocationPermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    }
}

//fun retrieveFirebaseToken() {
//    FirebaseMessaging.getInstance().token
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val token = task.result
//                AtasuaiApp.setCurrentAndroidToken(token)
//            } else {
//            }
//        }
//}
