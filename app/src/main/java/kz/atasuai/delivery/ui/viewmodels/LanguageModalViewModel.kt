package kz.atasuai.delivery.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.JsonHelper
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.market.models.LanguageModel

class LanguageModalViewModel : QarBaseViewModel() {

    private val _languageList = MutableLiveData<List<LanguageModel>>()
    val languageList: LiveData<List<LanguageModel>> get() = _languageList

    init {
        loadData()
    }
    private fun loadData() {
        viewModelScope.launch {
            val result = APIHelper.queryAsync("/language/list", "GET")
            result.fold(
                onSuccess = { ajaxMsg ->
                    if (ajaxMsg.status.equals("success", ignoreCase = true)) {
                        val languages: List<LanguageModel>? = JsonHelper.convertAnyToObject(ajaxMsg.data)
                        _languageList.postValue(languages ?: emptyList())
                        for (lan in languages ?: emptyList()) {
                            if (lan.culture == AtasuaiApp.currentLanguage.value.culture) {
                                AtasuaiApp.updateLanguage(lan)
                            }
                        }

                    } else {
                        _languageList.postValue(emptyList())
                    }
                },
                onFailure = { exception ->
                    _languageList.postValue(emptyList())
                }
            )
        }
    }
}
