package kz.atasuai.market.ui.components.welcome

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.ErrorType
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.RegexHelper
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.LoginViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun LoginComponent(modifier:Modifier, viewModel: LoginViewModel, currentLanguage: LanguageModel, context:Context, phone:String = ""){
    val canLogin by viewModel.canLogin.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val regexHelper = RegexHelper()
    val canVerify = regexHelper.isPhoneNumber(viewModel.phone) == viewModel.phone
    var focusedField by remember { mutableStateOf("%@#%325") }
    val confirmError by viewModel.confirmError.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.onPhoneNumberChange(phone)
    }
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
            TitleStyle(text=T("ls_Welcome",currentLanguage),fontSize = 22f,)
        }
        VSpacerHi(10f)
        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = T("ls_Eypntli2",currentLanguage),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(300),
                    color = Color(0xFF7B7E90),
                    textAlign = TextAlign.Center,
                )
            )
        }
        VSpacerHi(40f)

        Row(
            modifier = Modifier
                .fillMaxWidth()
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CountryPhonePrefix()
            Spacer(modifier = Modifier.width(8.dp))
            PhoneNumberField(
                phoneNumber = viewModel.phone,
                onPhoneNumberChange = { newPhoneNumber ->
                    viewModel.onPhoneNumberChange(newPhoneNumber)
                    viewModel.setConfirmError(ErrorType.Empty)
                    viewModel.onPasswordChange("")
                    viewModel.setCanLogin(false)
                },
                placeholderText = T("ls_Eypn", currentLanguage),
                confirmError = confirmError == ErrorType.Phone,
                onLogin = {
                    if(canVerify && !canLogin){
                        viewModel.verify(context,type = "register")
                    }
                    }
            )
        }
        if(canLogin){
            VSpacerHi(10f)
            PasswordField(
                password = viewModel.password,
                onPasswordChange = { newPassword ->
                    viewModel.onPasswordChange(newPassword)
                    focusedField = viewModel.password
                    viewModel.setConfirmError(ErrorType.Empty)
                },
                placeholderText = T("ls_Password", currentLanguage),
                confirmError = confirmError == ErrorType.Password,
                modifier = Modifier.fillMaxWidth()
                    .onFocusChanged { if (!it.isFocused) focusedField = "remove"},
                onLogin = {
                    if(canVerify && viewModel.password.isNotEmpty()){
                        viewModel.verify(context,type = "register")
                    }
                }
            )
            if(confirmError == ErrorType.Password){
                VSpacerHi(6f)
                Row(modifier=Modifier.fillMaxWidth()
                    .height(15.dp)
                    ,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    Text(
                        text = T("ls_Yetwp",currentLanguage),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFF4B4B),
                        )
                    )
                    Text(
                        text = T("ls_Forgotpassword",currentLanguage) + " ?",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF4F89FC),
                        ),
                        modifier=Modifier.noRippleClickable {
                            viewModel.verify(context,type = "recover")
                            viewModel.setLoginType(LoginType.Recover)
                        }
                    )

                }
                VSpacerHi(15f)
            }else{
                VSpacerHi(36f)
            }

        }else{
            VSpacerHi(96f)
        }

        GlobalButton(
            text = T("ls_Signin", currentLanguage),
            status = viewModel.buttonStatus,
            onClick = {
                if(!canLogin){
                    viewModel.verify(context,type = "register")
                }else{
                    if(canVerify && viewModel.password.isNotEmpty()){
                        viewModel.login(context)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .height(50.dp)

        )

    }


}