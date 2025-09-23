package kz.atasuai.delivery.ui.viewmodels.home

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel

class ScanQRViewModel : QarBaseViewModel() {
    private val _isScanQR = MutableStateFlow(true)
    val isScanQR: StateFlow<Boolean> = _isScanQR.asStateFlow()
    fun setIsOnline(value: Boolean) {
        _isScanQR.value = value
    }
    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult.asStateFlow()

    private val _isScanning = MutableStateFlow(true)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()


    private val _apiSuccess = MutableStateFlow(false)
    val apiSuccess: StateFlow<Boolean> = _apiSuccess.asStateFlow()

    private val _apiMessage = MutableStateFlow<String?>(null)
    val apiMessage: StateFlow<String?> = _apiMessage.asStateFlow()

    private val _confirmLogin = MutableStateFlow(false)
    val confirmLogin: StateFlow<Boolean> = _confirmLogin.asStateFlow()
    fun onConfirmLogin(value:Boolean) {
        _confirmLogin.value = value
    }
    var token = MutableStateFlow<String>("")

    fun onQRCodeScanned(result: String) {
        viewModelScope.launch {
            try {
                _scanResult.value = result
                _isScanning.value = false
                _errorMessage.value = null
                val extractedToken = extractTokenFromUrl(result)
                if (extractedToken != null) {
                    onConfirmLogin(true)
                    token.value = extractedToken
                } else {
                    _errorMessage.value = "无法从扫码结果中提取token"
                }
            } catch (e: Exception) {
                _errorMessage.value = "处理扫码结果时发生错误: ${e.message}"
            }
        }
    }


    private fun extractTokenFromUrl(url: String): String? {
        return try {
            val tokenRegex = """token=([^&]+)""".toRegex()
            val matchResult = tokenRegex.find(url)
            if (matchResult != null) {
                return matchResult.groupValues[1]
            }

            if (url.startsWith("http")) {
                val uri = Uri.parse(url)
                val token = uri.getQueryParameter("token")
                if (!token.isNullOrEmpty()) {
                    return token
                }
            }

            val tokenIndex = url.indexOf("token=")
            if (tokenIndex != -1) {
                val startIndex = tokenIndex + "token=".length
                val endIndex = url.indexOf("&", startIndex).let {
                    if (it == -1) url.length else it
                }
                return url.substring(startIndex, endIndex)
            }

            null
        } catch (e: Exception) {
            null
        }
    }

     fun loadTokenToScan(context: Context) {
        val paraDic = mapOf(
            "token" to token.value
        )

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null
                _apiSuccess.value = false

                val result = APIHelper.queryAsync("/user/auth/scan", "POST", paraDic = paraDic)
                result.fold(
                    onSuccess = { ajaxMsg ->
                        _isLoading.value = false
                        if (ajaxMsg.status.equals("success", ignoreCase = true)) {
                            _apiSuccess.value = true
                            _apiMessage.value = ajaxMsg.message ?: ""
                            delay(1000)
                            Navigator.navigate(context = context,destination = ActivityList.MainActivity,
                                clearTask = true
                                )
                        } else {
                            _apiSuccess.value = false
                            _errorMessage.value = ajaxMsg.message ?: "error"
                        }
                    },
                    onFailure = { exception ->
                        _isLoading.value = false
                        _apiSuccess.value = false
                        _errorMessage.value = "网络请求失败: ${exception.message}"
                    }
                )
            } catch (e: Exception) {
                _isLoading.value = false
                _apiSuccess.value = false
                _errorMessage.value = "调用API时发生错误: ${e.message}"
            }
        }
    }



    fun restartScanning() {
        viewModelScope.launch {
            _scanResult.value = null
            _isScanning.value = true
            _errorMessage.value = null
            _isLoading.value = false
            _apiSuccess.value = false
            _apiMessage.value = null
        }
    }

    fun onScanError(error: String) {
        viewModelScope.launch {
            _errorMessage.value = error
            _isScanning.value = false
        }
    }

    /**
     * 清除错误信息
     */
    fun clearError() {
        viewModelScope.launch {
            _errorMessage.value = null
        }
    }

    /**
     * 清除API消息
     */
    fun clearApiMessage() {
        viewModelScope.launch {
            _apiMessage.value = null
        }
    }

    fun retryTokenScan() {
        val currentResult = _scanResult.value
        if (currentResult != null) {
            val extractedToken = extractTokenFromUrl(currentResult)
            if (extractedToken != null) {
                onConfirmLogin(true)
                token.value = extractedToken
            } else {
                _errorMessage.value = "无法提取token，请重新扫码"
            }
        } else {
            _errorMessage.value = "没有扫码结果，请先扫码"
        }
    }

    /**
     * 验证URL格式是否包含token
     */
    fun validateQRContent(content: String): Boolean {
        return try {
            content.contains("token=") ||
                    (content.startsWith("http") && Uri.parse(content).getQueryParameter("token") != null)
        } catch (e: Exception) {
            false
        }
    }




    // 手电筒状态
    private val _isFlashlightOn = MutableStateFlow(false)
    val isFlashlightOn = _isFlashlightOn.asStateFlow()

    fun toggleFlashlight() {
        _isFlashlightOn.value = !_isFlashlightOn.value
    }

    fun onDestroy() {
        _isFlashlightOn.value = false
    }
}
