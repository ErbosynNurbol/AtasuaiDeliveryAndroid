package kz.atasuai.delivery.ui.viewmodels.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel

class HomeScreenViewModel: QarBaseViewModel(){
    private val  _changeSearch = MutableStateFlow(false)
    val changeSearch: StateFlow<Boolean> get() = _changeSearch
    fun setChangeSearch(value: Boolean) {
        _changeSearch.value = value;
    }
    private val _keyWord = MutableStateFlow("")
    val keyWord: StateFlow<String> = _keyWord.asStateFlow()

    fun setKeyWord(keyWord: String) {
        _keyWord.value = keyWord
    }
}