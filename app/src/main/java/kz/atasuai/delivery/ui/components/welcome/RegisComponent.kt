package kz.atasuai.market.ui.components.welcome

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.common.ErrorType
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.LoginViewModel
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.ui.viewmodels.welcome.RegisterViewModel

@Composable
fun RegisComponent(modifier: Modifier, viewModel: LoginViewModel, registerViewModel: RegisterViewModel, currentLanguage: LanguageModel, context: Context){
    val userName by registerViewModel.userName.collectAsState()
    val surName by registerViewModel.surName.collectAsState()
    val password by registerViewModel.password.collectAsState()
    val confirmPassword by registerViewModel.confirmPassword.collectAsState()
    val confirmError by registerViewModel.confirmError.collectAsState()
    val strength = registerViewModel.passwordStrength
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val canLogin = userName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
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
            TitleStyle(text= T("ls_Rits",currentLanguage),fontSize = 22f,)
        }
        VSpacerHi(33f)
        CustomTextField(
            text = userName,
            onTextChange = { newUserName ->
                registerViewModel.onUserNameChange(newUserName)
            },
            placeholderText = T("ls_Yourname", currentLanguage),
            confirmError = confirmError == ErrorType.UserName,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(11.dp))

        PasswordField(
            password = password,
            onPasswordChange = { newPassword ->
                registerViewModel.onPasswordChange(newPassword)
            },
            placeholderText = T("ls_Peanp", currentLanguage),
            confirmError = confirmError == ErrorType.Password,
            modifier = Modifier.fillMaxWidth(),
            onLogin = {
                if(canLogin){
                    registerViewModel.register(context, phone = viewModel.phone)
                }
            }
        )
        Spacer(modifier = Modifier.height(11.dp))
        PasswordField(
            password = confirmPassword,
            onPasswordChange = { newPassword ->
                registerViewModel.onConfirmPasswordChange(newPassword)
            },
            placeholderText = T("ls_Repeatthepassword", currentLanguage),
            confirmError = confirmError == ErrorType.Password,
            modifier = Modifier.fillMaxWidth(),
            onLogin = {
                if(canLogin){
                    registerViewModel.register(context, phone = viewModel.phone)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            if(confirmError == ErrorType.ConfirmPassword){
                Text(
                    text = T("ls_Confirmnewpassword",currentLanguage),
                    style = TextStyle(
                        fontSize = responsiveFontSize(14f),
                        lineHeight = responsiveFontSize(16f),
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFF03024),
                    )
                )
            }else{
                Text(
                    text = T("ls_Tpmcalcos", currentLanguage),
                    style = TextStyle(
                        fontSize = responsiveFontSize(12f),
                        lineHeight =  responsiveFontSize(15f),
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(300),
                        color = Color(0xFFABABAB),
                    ),
                    modifier = Modifier.weight(0.5f)
                )
                Spacer(modifier = Modifier.weight(0.2f))
                PasswordStrengthIndicator(
                    strength = strength,
                    modifier = Modifier.weight(0.3f)
                )
            }

        }
       VSpacerHi(16f)
        GlobalButton(
            text = T("ls_Register", currentLanguage),
            status = registerViewModel.buttonStatus,
            onClick = {
                registerViewModel.register(context, phone = viewModel.phone)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)

        )
    }
}