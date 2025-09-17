package kz.atasuai.delivery.ui.viewmodels.welcome

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel

class RegisDeliveryViewModel:QarBaseViewModel(){
    private val _deliveryType = MutableStateFlow(DeliveryType.Car)
    val deliveryType: StateFlow<DeliveryType> = _deliveryType.asStateFlow()
    fun setDeliveryType(value: DeliveryType) {
        _deliveryType.value = value
    }

}

enum class DeliveryType{
    Car,
    Motorcycle,
    Walking
}