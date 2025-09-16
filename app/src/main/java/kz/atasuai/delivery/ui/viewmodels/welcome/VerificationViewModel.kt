package kz.atasuai.market.ui.viewmodels.welcome

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.RegexHelper
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.common.VerificationType
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.State
import kz.atasuai.market.models.AjaxMsgModel
import java.security.MessageDigest
import java.util.regex.Pattern

class VerificationViewModel : QarBaseViewModel() {

    private val _otpCode = MutableStateFlow("")
    val otpCode: StateFlow<String> = _otpCode.asStateFlow()

    private val _currentFocus = MutableStateFlow(0)
    val currentFocus: StateFlow<Int> = _currentFocus.asStateFlow()

    fun updateCurrentFocus(position: Int) {
        _currentFocus.value = position.coerceIn(0, 3)
    }

    private val _timer = MutableStateFlow(AtasuaiApp.resendSmsTimeSeconds)
    val timer: StateFlow<Int> = _timer.asStateFlow()

    private val _canResendCode = MutableStateFlow(false)
    val canResendCode: StateFlow<Boolean> = _canResendCode.asStateFlow()

    private val _verificationFailed = MutableStateFlow(false)
    val verificationFailed: StateFlow<Boolean> = _verificationFailed.asStateFlow()

    var phoneNumber by mutableStateOf("") // 新增: 存储电话号码
        private set

    private val _sendCode = MutableStateFlow(false)
    val sendCode: StateFlow<Boolean> = _sendCode.asStateFlow()

    fun setSendCode(value: Boolean) {
        _sendCode.value = value
    }

    fun setVerificationStatus(value: Boolean) {
        _verificationFailed.value = value
    }

    fun initializeTimer(timerStartTime: Int) {
        _timer.value = timerStartTime
        startTimer()
    }
    fun resetTimerState() {
        _canResendCode.value = false
        _timer.value = AtasuaiApp.resendSmsTimeSeconds
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_timer.value > 0) {
                delay(1000L)
                _timer.value -= 1
            }
            _canResendCode.value = true
        }
    }

    fun onOtpChange(newOtpCode: String, context: Context, type: String, phoneNumber: String) {
        _otpCode.value = newOtpCode
        if (newOtpCode.length == 4 && newOtpCode.all { it.isDigit() }) {
            checkCode(phoneNumber, type, context, newOtpCode)
        }
    }

    private fun resetTimer() {
        _canResendCode.value = false
        _timer.value = AtasuaiApp.resendSmsTimeSeconds
        startTimer()
    }

    private var smsRetrieverTask: Task<Void>? = null
    private var smsReceiver: MySmsBroadcastReceiver? = null

    fun sendSms(phone: String, type: String, context: Context) {
        val regexHelper = RegexHelper()
        val isValid = regexHelper.isPhoneNumber(phone) == phone
        phoneNumber = if (isValid) regexHelper.formatPhoneNumber(phone).toString() else phone
        val account = if (type == "email") phone else phoneNumber
        val lastSendSMSTimestamp = AtasuaiApp.getLastSendSMSTimestamp(account)
        val currentTime = System.currentTimeMillis() / 1000
        if (shouldSendOtp(lastSendSMSTimestamp, currentTime)) {
            sendPhoneVerifyCode(
                account,
                type,
                onSuccess = { response ->
                    if (response.status.equals("success", ignoreCase = true)) {
                        AtasuaiApp.saveLastSendSMSTimestamp(account, currentTime)
                        setSendCode(true)
                        startSmsRetriever(context)
                        registerSmsReceiver(context, type,phoneNumber=phone)
                        ToastHelper.showMessage(context, ToastType.SUCCESS, response.message)
                    } else {
                        ToastHelper.showMessage(context, ToastType.WARNING, response.message)
                    }
                },
                onFailure = { exception ->
                    ToastHelper.showMessage(context, ToastType.ERROR, exception.message ?: "Unknown error occurred")
                }
            )
        }
    }

    fun resendCode(phoneNumber: String, type: String, context: Context) {
        sendPhoneVerifyCode(phoneNumber, type,
            onSuccess = { response ->
                if (response.status.equals("success", ignoreCase = true)) {
                    AtasuaiApp.saveLastSendSMSTimestamp(phoneNumber, System.currentTimeMillis() / 1000)
                    this.phoneNumber = phoneNumber // 更新phoneNumber
                    ToastHelper.showMessage(context, ToastType.SUCCESS, response.message)
                    resetTimer()
                    startSmsRetriever(context)
                    registerSmsReceiver(context, type,phoneNumber=phoneNumber)
                } else {
                    ToastHelper.showMessage(context, ToastType.WARNING, response.message)
                }
            },
            onFailure = { throwable ->
                ToastHelper.showMessage(context, ToastType.ERROR, throwable.message ?: "Unknown error occurred")
            }
        )
    }

    private fun shouldSendOtp(lastSendSMSTimestamp: Long?, currentTime: Long): Boolean {
        return lastSendSMSTimestamp == null || currentTime - lastSendSMSTimestamp >= 60
    }

    private fun startSmsRetriever(context: Context) {
        val client = SmsRetriever.getClient(context)
        smsRetrieverTask = client.startSmsRetriever()
        smsRetrieverTask?.addOnSuccessListener {
            Log.d("VerificationViewModel", "SMS Retriever started successfully")
        }?.addOnFailureListener { e ->
            Log.e("VerificationViewModel", "SMS Retriever failed: ${e.message}")
        }
    }

    private fun registerSmsReceiver(context: Context, type: String, phoneNumber: String) {
        smsReceiver = MySmsBroadcastReceiver { otp ->
            onOtpChange(otp, context, type,phoneNumber =phoneNumber)
        }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        context.registerReceiver(smsReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED)
    }

    fun unregisterSmsReceiver(context: Context) {
        smsReceiver?.let {
            context.unregisterReceiver(it)
        }
        smsReceiver = null
    }

    private fun checkPhoneVerifyCode(phone: String, type: String, verifyCode: String, onSuccess: (AjaxMsgModel) -> Unit = {}, onFailure: (Throwable) -> Unit = {}, context: Context) {
        val regexHelper = RegexHelper()
        val isValid = regexHelper.isPhoneNumber(phone) == phone
        val phoneNumber =  if(isValid) regexHelper.formatPhoneNumber(phone) else phone
        viewModelScope.launch {
            setLoading(true)
            val result = APIHelper.queryAsync(
                url = "/user/check",
                method = "POST",
                paraDic = mapOf(
                    "phoneNumber" to phoneNumber.let{
                        phoneNumber ?: ""
                    },
                    "type" to type,
                    "verifyCode" to verifyCode
                )
            )
            result.fold(
                onSuccess = { ajaxMsg ->
                    if (ajaxMsg.status.equals("success", ignoreCase = true)) {
                        updateState(State.Success)
                        onSuccess(ajaxMsg)
                    } else {
                        updateState(State.Error)
                        onFailure(Exception(ajaxMsg.message ?: "Verification failed"))
                    }
                },
                onFailure = { throwable ->
                    updateState(State.Error)
                    onFailure(throwable)
                }
            )
            setLoading(false)
        }
    }

    private fun checkCompanyCode(iinBin: String, verifyCode: String, onSuccess: (AjaxMsgModel) -> Unit = {}, onFailure: (Throwable) -> Unit = {}) {
        viewModelScope.launch {
            setLoading(true)
            val result = APIHelper.queryAsync(
                url = "/api/user/verification",
                method = "POST",
                paraDic = mapOf(
                    "iinBin" to iinBin,
                    "code" to verifyCode
                )
            )
            result.fold(
                onSuccess = { ajaxMsg ->
                    if (ajaxMsg.status.equals("success", ignoreCase = true)) {
                        updateState(State.Success)
                        onSuccess(ajaxMsg)
                    } else {
                        updateState(State.Error)
                        onFailure(Exception(ajaxMsg.message ?: "Verification failed"))
                    }
                },
                onFailure = { throwable ->
                    updateState(State.Error)
                    onFailure(throwable)
                }
            )
            setLoading(false)
        }
    }
     private val _isCheckCode = MutableStateFlow(false)
    val isCheckCode: StateFlow<Boolean> = _isCheckCode.asStateFlow()
    fun setCheckCode(value: Boolean) {
        _isCheckCode.value = value
    }
    fun checkCode(phoneNumber: String, type: String, context: Context, verifyCode: String) {
        if (isLoading.value) return
        checkPhoneVerifyCode(phoneNumber, type, verifyCode,context=context,
            onSuccess = { response ->
                setLoading(false)
                if (response.status.equals("success", ignoreCase = true)) {
                    setVerificationStatus(false)
                    if (type == VerificationType.Register.toString()) {
                        setCheckCode(true)
                        setLoginType(LoginType.Regis)
                        val token = response.data.toString()
                        AtasuaiApp.setCurrentPerson(token);
                        ToastHelper.showMessage(context, ToastType.SUCCESS, response.message)
                        Navigator.navigate(context = context, ActivityList.MainActivity,clearTask = true)
                        AtasuaiApp.updateIsFirstRegis(true)
                    } else {
                        val token = response.data.toString()
                        AtasuaiApp.setCurrentPerson(token);
                        ToastHelper.showMessage(context, ToastType.SUCCESS, response.message)
                        Navigator.navigate(context = context, ActivityList.MainActivity,clearTask = true)
                    }
                } else {
                    setVerificationStatus(true)
                    ToastHelper.showMessage(context, ToastType.WARNING, response.message)
                }
            },
            onFailure = { exception ->
                setLoading(false)
                setVerificationStatus(true)
                ToastHelper.showMessage(context, ToastType.ERROR, exception.message ?: "Unknown error occurred")
            }
        )
    }

    fun getAppHash(context: Context): String? {
        return try {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            info.signatures?.firstOrNull()?.let { signature ->
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Base64.encodeToString(md.digest(), Base64.NO_WRAP).substring(0, 11)
            }
        } catch (e: Exception) {
            Log.e("VerificationViewModel", "Failed to get app hash: ${e.message}")
            null
        }
    }
    protected open fun sendPhoneVerifyCode(
        phone: String,
        type: String,
        onSuccess: (AjaxMsgModel) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        val regexHelper = RegexHelper()
        val isValid = regexHelper.isPhoneNumber(phone) == phone
        val phoneNumber =  if(isValid) regexHelper.formatPhoneNumber(phone) else phone
        viewModelScope.launch {
            setLoading(true)
            val result = APIHelper.queryAsync(
                url = "/user/verify",
                method = "POST",
                paraDic = mapOf(
                    "phoneNumber" to phoneNumber.let {
                        phoneNumber ?: ""
                    },
                    "type" to type,
                )
            )
            result.fold(
                onSuccess = { ajaxMsg ->
                    updateState(State.Success)
                    onSuccess(ajaxMsg)
                },
                onFailure = { throwable ->
                    updateState(State.Error)
                    onFailure(throwable)
                }
            )
            setLoading(false)
        }
    }
}

class MySmsBroadcastReceiver(private val onOtpReceived: (String) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent.action) {
            val extras = intent.extras ?: return
            val status = extras[SmsRetriever.EXTRA_STATUS] as? Status ?: return
            when (status.statusCode) {
                CommonStatusCodes.SUCCESS -> {
                    val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as? String
                    message?.let {
                        val otp = extractOtpFromMessage(it)
                        if (otp.isNotEmpty()) {
                            onOtpReceived(otp)
                        }
                    }
                }
                CommonStatusCodes.TIMEOUT -> {
                    Log.d("MySmsReceiver", "SMS retrieval timed out")
                }
                else -> {
                    Log.e("MySmsReceiver", "SMS retrieval error: ${status.statusCode}")
                }
            }
        }
    }

    private fun extractOtpFromMessage(message: String): String {
        val pattern = Pattern.compile("(\\d{4})")
        val matcher = pattern.matcher(message)
        return if (matcher.find()) matcher.group(0) ?: "" else ""
    }


}