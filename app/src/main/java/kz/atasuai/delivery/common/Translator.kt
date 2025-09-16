package kz.atasuai.delivery.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.market.common.JsonHelper
import kz.atasuai.market.common.LanguagePackHelper
import kz.atasuai.market.models.LanguageModel
import java.util.concurrent.ConcurrentHashMap

object Translator {

    private var langDictionary: MutableMap<String, String>? = null

    fun loadLanguagePack(language: LanguageModel, callback: (Boolean) -> Unit) {
        val currentLanguage = AtasuaiApp.currentLanguage.value
        if (language.culture != currentLanguage.culture) {
            langDictionary = null
        }
        if (langDictionary != null) {
            callback(true)
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val jsonLanguagePack = withContext(Dispatchers.IO) {
                LanguagePackHelper.getLanguagePackJsonString(
                    language.culture,
                    AtasuaiApp.appContext
                )
            }

            if (jsonLanguagePack.isNotEmpty()) {
                try {
                    langDictionary =
                        JsonHelper.deserializeObject<ConcurrentHashMap<String, String>>(
                            jsonLanguagePack
                        )
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(false)
                    return@launch
                }
            }

            callback(true)
        }
    }

    fun T(localString: String, language: LanguageModel? = null): String {
        language?.let {
            loadLanguagePack(it) { }
        }

        return langDictionary?.get(localString) ?: localString
    }
}