package kz.atasuai.market.ui.components.welcome

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.OtpInput
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.LoginViewModel
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.ui.viewmodels.welcome.VerificationViewModel

@Composable
fun RecoverComponent(modifier: Modifier, viewModel: LoginViewModel, verificationViewModel: VerificationViewModel, currentLanguage: LanguageModel, context: Context){
    val otpCode by verificationViewModel.otpCode.collectAsState()
    val sendCode by verificationViewModel.sendCode.collectAsState()
    val timer by verificationViewModel.timer.collectAsState()
    val lastSendSMSTimestamp = AtasuaiApp.getLastSendSMSTimestamp(viewModel.phone)
    val currentTime = System.currentTimeMillis() / 1000
    var timerStartTime = AtasuaiApp.resendSmsTimeSeconds
    val currentFocus by verificationViewModel.currentFocus.collectAsState()
    LaunchedEffect(Unit) {
        verificationViewModel.resetTimerState()
    }
    if (lastSendSMSTimestamp != null && currentTime - lastSendSMSTimestamp < 120) {
        timerStartTime = AtasuaiApp.resendSmsTimeSeconds - (currentTime - lastSendSMSTimestamp).toInt()
    }
    if (timer == AtasuaiApp.resendSmsTimeSeconds) {
        verificationViewModel.initializeTimer(timerStartTime)
    }
    val canResendCode by verificationViewModel.canResendCode.collectAsState()
    val verificationFailed by verificationViewModel.verificationFailed.collectAsState()
    val loginType by viewModel.loginType.collectAsState()
    val type = when(loginType){
        LoginType.Recover -> "recover"
        LoginType.SmsLogin -> "smslogin"
        LoginType.Regis -> "register"
        else -> "login"
    }
    var borderColor by remember { mutableStateOf(PrimaryColor)}

    LaunchedEffect(verificationFailed) {
        borderColor = if (verificationFailed) {
            Color.Red
        } else {
            PrimaryColor
        }
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = modifier
        .noRippleClickable {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    ){
        VSpacerHi(110f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            TitleStyle(text= T("ls_Confirmationcode",currentLanguage),fontSize = 22f,)
        }
        VSpacerHi(10f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = T("ls_Whsasctynnpetcb",currentLanguage).replace("{number}",viewModel.phone),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(300),
                    color = Color(0xFF7B7E90),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier.width(260.dp)
            )
        }
        VSpacerHi(50f)
        OtpInput(
            otpCode = otpCode,
            currentFocus = currentFocus,
            onOtpChange = { code ->
                verificationViewModel.onOtpChange(
                    code,
                    context,
                    type = type,
                    phoneNumber = viewModel.phone
                )
            },
            onFocusChange = { position ->
                verificationViewModel.updateCurrentFocus(position)
            },
            error = verificationFailed,
            isAddPhone = true,
            onErrorCleared = { verificationViewModel.setVerificationStatus(false) }
        )
        Spacer(modifier = Modifier.height(24.dp))

        if (timer > 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = T("ls_Resendsmscode", currentLanguage),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFA6A6A6)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "%02d:%02d".format(timer / 60, timer % 60),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (canResendCode) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = T("ls_Resendcode", currentLanguage),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor,
                    modifier = Modifier.noRippleClickable {
                        verificationViewModel.resendCode(viewModel.phone, type = type, context)
                    }
                )
            }

        }
    }
}