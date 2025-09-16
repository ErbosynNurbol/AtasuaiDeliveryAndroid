package kz.atasuai.delivery.ui.viewmodels.welcome

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.ErrorType
import kz.atasuai.delivery.common.RegexHelper
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.common.VerificationType
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.activities.MainActivity
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.market.models.AjaxMsgModel
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.viewmodels.State


class LoginViewModel: QarBaseViewModel(){

    var phone by mutableStateOf( "")
        private set

    var password by mutableStateOf("")
        private set

    var buttonStatus by mutableStateOf(ButtonStatus.Disabled)
        private set

    private val _canLogin = MutableStateFlow(false)
    val canLogin: StateFlow<Boolean> = _canLogin.asStateFlow()
    fun setCanLogin(value: Boolean) {
        _canLogin.value = value
    }
    fun toggleBtnStatus(){
        when(_loginType.value){
            LoginType.Login -> SetLoginBtn()
           else -> SetLoginBtn()
        }
    }
    fun SetLoginBtn(){
        val regexHelper = RegexHelper()
        buttonStatus = if(canLogin.value){
            if(regexHelper.isPhoneNumber(phone) == phone && password.isNotEmpty()){
                ButtonStatus.Enabled
            }else{
                ButtonStatus.Disabled
            }
        }else{
            if(regexHelper.isPhoneNumber(phone) == phone){
                ButtonStatus.Enabled
            }else{
                ButtonStatus.Disabled
            }
        }


    }

    fun onPhoneNumberChange(newPhoneNumber: String) {
        phone = newPhoneNumber
        toggleBtnStatus()
    }
    fun onPasswordChange(newPassword: String) {
        password = newPassword
        toggleBtnStatus()
    }


    fun login(context: Context) {
        val regexHelper = RegexHelper()
        val isValid = regexHelper.isPhoneNumber(phone) == phone
        val phoneNumber =  if(isValid) regexHelper.formatPhoneNumber(phone) else phone
        viewModelScope.launch {
            buttonStatus = ButtonStatus.Loading
            val result = APIHelper.queryAsync(
                url = "/user/login",
                method = "POST",
                paraDic = mapOf(
                    "phoneNumber" to phoneNumber.let {
                        phoneNumber ?: ""
                    },
                    "password" to password,
//                    "AndroidToken" to AtasuaiApp.currentAndroidToken,
                )
            )
            result.fold(
                onSuccess = { response ->
                    if (response.status.equals("success", ignoreCase = true)) {
                        var token = response.data.toString()
                        AtasuaiApp.setCurrentPerson(token);
                        ToastHelper.showMessage(context, ToastType.SUCCESS, response.message)
                        delay(1000)
                        val intent = Intent(context, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        context.startActivity(intent)
                    } else {
                        if(response.data.toString().equals("login",ignoreCase = true)){
                            setConfirmError(ErrorType.Phone)
                        }else if(response.data.toString().equals("password",ignoreCase = true)){
                            setConfirmError(ErrorType.Password)
                        }
                        ToastHelper.showMessage(context, ToastType.WARNING, response.message)
                    }
                    buttonStatus = ButtonStatus.Enabled
                },
                onFailure = { throwable ->
                    buttonStatus = ButtonStatus.Enabled
                    ToastHelper.showMessage(context, ToastType.ERROR,  T("ls_Oswwptal", AtasuaiApp.currentLanguage.value))
                }
            )
            buttonStatus = ButtonStatus.Enabled
        }
    }

    private fun shouldSendOtp(lastSendSMSTimestamp: Long?, currentTime: Long): Boolean {
        return lastSendSMSTimestamp == null || currentTime - lastSendSMSTimestamp >= 60
    }
    private val _hasPhoneModal = MutableStateFlow(false)
    val hasPhoneModal: StateFlow<Boolean> = _hasPhoneModal.asStateFlow()
    fun setHasPhoneModal(value: Boolean) {
        _hasPhoneModal.value = value
    }
    fun sendSms(context: Context,type: VerificationType){
        buttonStatus = ButtonStatus.Loading
        val regexHelper = RegexHelper()
        val isValid = regexHelper.isPhoneNumber(phone) == phone
        val phoneNumber =  if(isValid) regexHelper.formatPhoneNumber(phone) else phone
        val lastSendSMSTimestamp = phoneNumber?.let { AtasuaiApp.getLastSendSMSTimestamp(it) }
        val currentTime = System.currentTimeMillis() / 1000
        if (shouldSendOtp(lastSendSMSTimestamp, currentTime)) {
            if (phoneNumber != null) {
                sendPhoneVerifyCode(phoneNumber, type.toString(),
                    onSuccess = { response ->
                        if(response.status.equals("success",ignoreCase = true)){
                            AtasuaiApp.saveLastSendSMSTimestamp(phoneNumber, currentTime)
                            if(type == VerificationType.Register){
                                setCanLogin(false)
                            }
                        }else{
                            if(response.data.toString().equals("login",ignoreCase = true)){
                                setCanLogin(true)
                                if(response.message == T("ls_Tniar", AtasuaiApp.currentLanguage.value)){
                                    setHasPhoneModal(true)
                                }else{
                                    ToastHelper.showMessage(context, ToastType.WARNING, response.message);
                                }

                            }else{
                                ToastHelper.showMessage(context, ToastType.WARNING, response.message);
                                if(type == VerificationType.Register){
                                    setCanLogin(false)
                                }
                            }
                        }
                        buttonStatus = ButtonStatus.Enabled
                        forgetPwd = ButtonStatus.Enabled
                    },
                    onFailure = { exception ->
                        Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                        buttonStatus = ButtonStatus.Enabled
                        forgetPwd = ButtonStatus.Enabled
                    }
                )
            }
        } else {
        }


    }
    var forgetPwd by mutableStateOf(ButtonStatus.Enabled)
    fun recover(context: Context) {
        sendSms(context, VerificationType.Recover)
        forgetPwd = ButtonStatus.Disabled
    }
    protected open fun sendPhoneVerifyCode(
        phone: String,
        type: String,
        onSuccess: (AjaxMsgModel) -> Unit = {},
        onFailure: (Throwable) -> Unit = {}
    ) {
        viewModelScope.launch {
            setLoading(true)
            val result = APIHelper.queryAsync(
                url = "/Api/User/SendPhoneVerifyCode",
                method = "POST",
                paraDic = mapOf(
                    "account" to phone,
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
    fun verify(
        context:Context,
        type:String,
    ) {
        viewModelScope.launch {
            val regexHelper = RegexHelper()
          val phoneNumber = regexHelper.formatPhoneNumber(phone) ?: ""
            val result = APIHelper.queryAsync(
                url = "/user/verify",
                method = "POST",
                paraDic = mapOf(
                    "phoneNumber" to phoneNumber,
                    "type" to type,
                )
            )
            result.fold(
                onSuccess = { ajaxMsg ->
                    if(ajaxMsg.status.equals("success",ignoreCase = true)){
                        if(ajaxMsg.data.toString().equals("smslogin")){
                            setLoginType(LoginType.SmsLogin)
                        }else if(ajaxMsg.data.toString().equals("register")){
                            setLoginType(LoginType.Regis)
                        }else{
                            setLoginType(LoginType.Recover)
                        }
                        setCanLogin(false)
                    }else{
                        if(ajaxMsg.data.toString().equals("login",ignoreCase = true)){
                            setLoginType(LoginType.Login)
                            setCanLogin(true)
                        }else{
                            ToastHelper.showMessage(context, ToastType.WARNING, ajaxMsg.message);
                            setCanLogin(false)
                        }
                    }
                },
                onFailure = { throwable ->
                    buttonStatus = ButtonStatus.Enabled
                    ToastHelper.showMessage(context, ToastType.ERROR,  T("ls_Oswwptal", AtasuaiApp.currentLanguage.value))
                }
            )
            setLoading(false)
        }
    }
}
