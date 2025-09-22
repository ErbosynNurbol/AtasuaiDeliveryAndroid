package kz.atasuai.delivery.ui.viewmodels.order

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel

class OrderViewModel:QarBaseViewModel() {
    private val _isPlanned = MutableStateFlow(true)
    val isPlanned: StateFlow<Boolean> = _isPlanned.asStateFlow()
    fun updateOrderType(value:Boolean){
        _isPlanned.value = value
    }

}

enum class OrderType{
    Planned,
    Opportunistic
}