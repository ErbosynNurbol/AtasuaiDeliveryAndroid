package kz.atasuai.delivery.ui.activities.welcome

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kmpImagePicker.noRippleClickable
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.LoginType
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.common.Translator
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.ActivityTitle
import kz.atasuai.delivery.ui.components.global.LanguageModal
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.LanguageModalViewModel
import kz.atasuai.delivery.ui.viewmodels.welcome.LoginViewModel
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.ui.components.welcome.LoginComponent
import kz.atasuai.market.ui.components.welcome.RecoverComponent
import kz.atasuai.market.ui.viewmodels.welcome.RegisterViewModel
import kz.atasuai.market.ui.viewmodels.welcome.VerificationViewModel


class LoginActivity:ComponentActivity(){
    private val themeManager by lazy { AtasuaiApp.themeManager }
    val viewModel: LoginViewModel by viewModels()
    val languageView: LanguageModalViewModel by viewModels()
    val verificationViewModel: VerificationViewModel by viewModels()
    val regisViewModel : RegisterViewModel by viewModels()
    val phone by lazy {
        intent.getStringExtra("phone") ?: ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeManager.themeMode.collectAsState()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }

            val context = LocalContext.current
            val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
            AtasuaiTheme(darkTheme = darkTheme) {
                AtasuaiScreen(
                    applySystemBarsPadding = false,
                    statusBarDarkIcons = true,
                    navigationBarDarkIcons = true,
                    navigationBarColor = AtasuaiTheme.colors.background
                ) { screenModifier ->
                    LoginScreen(viewModel,verificationViewModel,regisViewModel,languageView,currentLanguage,context,modifier = screenModifier,phone)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel, verificationViewModel:VerificationViewModel, regisViewModel:RegisterViewModel, languageView: LanguageModalViewModel, currentLanguage: LanguageModel, context: Context, modifier: Modifier = Modifier,
                phone:String = ""
                ){
    var showLanguageModal by remember { mutableStateOf(false) }
    val loginType by viewModel.loginType.collectAsState()
    val isCheckCode by verificationViewModel.isCheckCode.collectAsState()
    if (showLanguageModal) {
        LanguageModal(
            onDismissRequest = { showLanguageModal = false },
            onLanguageSelected = { language ->
                Translator.loadLanguagePack(language) { success ->
                    if (success) {
                        AtasuaiApp.updateLanguage(language)
                        showLanguageModal = false
                    }
                }
            },
            viewModel = languageView
        )
    }
    Column(modifier=modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.background)
        .navigationBarsPadding()
        .imePadding()
        .padding(horizontal = 20.dp)

    ){
        VSpacerHi(54f)
        Box(modifier=Modifier.fillMaxWidth()
            ,
            contentAlignment = Alignment.CenterStart
        ){
            ActivityTitle(text = T("ls_Help",currentLanguage), fontSize = 14f, fontWeight = 400 ,modifier = Modifier.align(
                Alignment.CenterStart))
            Image(
                painter = painterResource(id = R.drawable.app_dark_logo),
                contentDescription = "shop_share_icon",
                modifier=Modifier
                    .padding(0.dp)
                    .width(105.92014.dp)
                    .height(23.dp)
                    .noRippleClickable {


                    }
                    .align(Alignment.Center)
            )
            Row(
                modifier = Modifier
                    .noRippleClickable {
                        showLanguageModal = true
                    }
                    .align(
                    Alignment.CenterEnd)
                ,verticalAlignment = Alignment.CenterVertically){
                ActivityTitle(text = currentLanguage.shortName, fontSize = 14f, fontWeight = 400)
                VSpacerWi(4f)
                Icon(
                    painter = painterResource(id = R.drawable.app_dark_logo),
                    contentDescription = "arrow down",
                    tint = Color(0xFF181D27),
                    modifier = Modifier.size(11.dp)

                )
            }
        }
        if(loginType == LoginType.Login){
            LoginComponent(modifier=Modifier.fillMaxSize(),viewModel,currentLanguage,context,phone)
        }
//        else if(isCheckCode && loginType == LoginType.Regis){
//            RegisComponent(modifier=Modifier.fillMaxSize(),viewModel,regisViewModel,currentLanguage,context)
//        }
        else if(loginType == LoginType.Recover || loginType == LoginType.SmsLogin || (!isCheckCode && loginType == LoginType.Regis)){
            RecoverComponent(modifier=Modifier.fillMaxSize(),viewModel,verificationViewModel,currentLanguage,context)
        }

    }
}