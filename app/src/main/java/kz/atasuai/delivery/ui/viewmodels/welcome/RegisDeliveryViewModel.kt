package kz.atasuai.delivery.ui.viewmodels.welcome

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.market.models.LanguageModel

class RegisDeliveryViewModel:QarBaseViewModel(){
    private val _documentData = MutableStateFlow(DocumentData())
    val documentData: StateFlow<DocumentData> = _documentData.asStateFlow()

    private val _deliveryType = MutableStateFlow(DeliveryType.Car)
    val deliveryType: StateFlow<DeliveryType> = _deliveryType.asStateFlow()
    fun setDeliveryType(value: DeliveryType) {
        _deliveryType.value = value
    }
    private val _documentType = MutableStateFlow(DocumentType.IDCardFront)
    val documentType:StateFlow<DocumentType> = _documentType.asStateFlow()
    fun setDocumentType(value: DocumentType){
        _documentType.value = value
    }
    fun getCurrentDocumentUri(): Uri {
        return when (_documentType.value) {
            DocumentType.IDCardFront -> _documentData.value.idCardFront
            DocumentType.IDCardBack -> _documentData.value.idCardBack
            DocumentType.LicenseFront -> _documentData.value.licenseFront
            DocumentType.LicenseBack -> _documentData.value.licenseBack
            DocumentType.CarIDFront -> _documentData.value.carIDFront
            DocumentType.CarIDBack -> _documentData.value.carIDBack
        }
    }

    fun updateDocument(documentType: DocumentType, uri: Uri) {
        val currentData = _documentData.value
        _documentData.value = when (documentType) {
            DocumentType.IDCardFront -> currentData.copy(idCardFront = uri)
            DocumentType.IDCardBack -> currentData.copy(idCardBack = uri)
            DocumentType.LicenseFront -> currentData.copy(licenseFront = uri)
            DocumentType.LicenseBack -> currentData.copy(licenseBack = uri)
            DocumentType.CarIDFront -> currentData.copy(carIDFront = uri)
            DocumentType.CarIDBack -> currentData.copy(carIDBack = uri)
        }
        updateButtonStatus()
    }

    // 移除指定类型的文档
    fun removeDocument(documentType: DocumentType) {
        updateDocument(documentType, Uri.EMPTY)
    }

    // 更新按钮状态的逻辑暂时移除，只负责显示照片
    private fun updateButtonStatus() {
        // TODO: 后续添加按钮状态逻辑
    }

    // 从相机或图库更新图片的统一入口
    fun updateCroppedImageUri(uri: Uri, context: Context, documentType: DocumentType) {
        updateDocument(documentType, uri)
    }

    fun deleteResource(context: Context) {
        viewModelScope.launch {
            val result = APIHelper.queryAsync("/user/delete/avatar", "POST")
            result.fold(
                onSuccess = { ajaxMsg ->
                    if (ajaxMsg.status.equals("success", ignoreCase = true)) {
                        val token = ajaxMsg.data.toString()
                        AtasuaiApp.setCurrentPerson(token);
                        ToastHelper.showMessage(context, ToastType.SUCCESS, ajaxMsg.message)
                    }else{
                        ToastHelper.showMessage(context, ToastType.WARNING, ajaxMsg.message)
                    }
                },
                onFailure = {exception ->
                    ToastHelper.showMessage(context, ToastType.ERROR,"$exception")
                }
            )
        }
    }
}

enum class DeliveryType{
    Car,
    Motorcycle,
    Walking
}

enum class DocumentType{
    IDCardFront,
    IDCardBack,
    LicenseFront,
    LicenseBack,
    CarIDFront,
    CarIDBack
}
data class DocumentData(
    val idCardFront: Uri = Uri.EMPTY,
    val idCardBack: Uri = Uri.EMPTY,
    val licenseFront: Uri = Uri.EMPTY,
    val licenseBack: Uri = Uri.EMPTY,
    val carIDFront: Uri = Uri.EMPTY,
    val carIDBack: Uri = Uri.EMPTY
)
