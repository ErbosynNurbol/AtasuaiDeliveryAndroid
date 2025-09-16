package kz.atasuai.market.ui.viewmodels.welcome

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.APIHelper
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.ErrorType
import kz.atasuai.delivery.common.StringHelper
import kz.atasuai.delivery.common.ToastHelper
import kz.atasuai.delivery.common.ToastType
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.activities.MainActivity
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel


class RegisterViewModel() : QarBaseViewModel() {


    private var verifyCode by mutableStateOf("")
        private set

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> get() = _userName

    private val _surName = MutableStateFlow("")
    val surName: StateFlow<String> get() = _surName

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> get() = _confirmPassword

    var passwordStrength by mutableIntStateOf(0)
        private set

    var buttonStatus by mutableStateOf(ButtonStatus.Disabled)
        private set


    fun setButtonStatus(){
        buttonStatus = if (userName.value.isNotEmpty() &&
            password.value.isNotEmpty() &&
            confirmPassword.value.isNotEmpty()) {
            ButtonStatus.Enabled
        } else {
            ButtonStatus.Disabled
        }
    }

    //initializeTimer
    fun onUserNameChange(newUserName: String) {
        _userName.value = newUserName
        setButtonStatus()
    }

    fun onSurNameChange(newSurName: String) {
        _surName.value = newSurName
        setButtonStatus()
    }


    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        passwordStrength = StringHelper.calculatePasswordStrength(newPassword)
        setButtonStatus()
    }

    fun onConfirmPasswordChange(newPassword: String) {
        _confirmPassword.value = newPassword
        setButtonStatus()
    }
    fun register(context: Context,phone:String) {
         viewModelScope.launch {
                buttonStatus = ButtonStatus.Loading
                val result = APIHelper.queryAsync(
                    url = "/user/verify",
                    method = "POST",
                    paraDic = mapOf(
                        "Phone" to phone,
                        "UserName" to userName.value,
                        "Password" to password.value,
                        "ConfirmPassword" to confirmPassword.value,
//                        "AndroidToken" to AtasuaiApp.currentAndroidToken,
                    )
                )
                result.fold(
                    onSuccess = { response ->
                        if (response.status.equals("success", ignoreCase = true)) {
                            var token = response.data.toString()
                            AtasuaiApp.setCurrentPerson(token);
                            delay(1000)
                            val intent = Intent(context, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                            context.startActivity(intent)
                        } else {
                            if(response.data != null){
                                when(response.data.toString()){
                                    "UserName" -> setConfirmError(ErrorType.UserName)
                                    "Password" -> setConfirmError(ErrorType.Password)
                                    "ConfirmPassword" ->setConfirmError(ErrorType.ConfirmPassword)
                                    else -> ToastHelper.showMessage(context, ToastType.ERROR, response.message)
                                }
                            }
                        }
                        buttonStatus = ButtonStatus.Enabled
                    },
                    onFailure = { throwable ->
                        buttonStatus = ButtonStatus.Enabled
                        ToastHelper.showMessage(context, ToastType.ERROR, throwable.message ?: "Unknown error occurred")
                    }
                )
                setLoading(false)
            }
    }


}