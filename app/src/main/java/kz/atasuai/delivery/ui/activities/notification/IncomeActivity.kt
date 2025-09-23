package kz.atasuai.delivery.ui.activities.notification

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.ActivityTitle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.EmptyDesStyle
import kz.atasuai.delivery.ui.theme.EmptyTitleStyle
import kz.atasuai.delivery.ui.theme.ProposalNameStyle
import kz.atasuai.delivery.ui.viewmodels.notification.NotifiType
import kz.atasuai.delivery.ui.viewmodels.notification.NotificationViewModel
import kz.atasuai.market.models.LanguageModel

class IncomeActivity: ComponentActivity() {
    private val themeManager by lazy { AtasuaiApp.themeManager }
    val viewModel: NotificationViewModel by viewModels()
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
                    IncomeComponent(viewModel,currentLanguage,context,modifier = screenModifier)
                }
            }
        }
    }
}

@Composable
fun IncomeComponent(viewModel: NotificationViewModel,currentLanguage: LanguageModel, context: Context, modifier: Modifier = Modifier,){
    Column(modifier = modifier.fillMaxSize()){
        Column(modifier=Modifier.fillMaxWidth()
            .height(responsiveWidth(100f))
            .background(AtasuaiTheme.colors.welcomeBac)
            .padding(horizontal = responsiveWidth(20f))
        ){
            VSpacerHi(54f)
            Box(modifier=Modifier.fillMaxWidth()
                ,
                contentAlignment = Alignment.CenterStart
            ){
                Icon(
                    painter = painterResource(id = R.drawable.back_left_icon),
                    contentDescription = "arrow down",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(16.dp)
                        .align(Alignment.CenterStart)
                        .noRippleClickable {
                            viewModel.onBack(context)
                        }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                    ,verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.score_icon),
                        contentDescription = "shop_share_icon",
                        modifier=Modifier
                            .padding(0.dp)
                            .size(25.dp)
                    )
                    VSpacerWi(10f)
                    Text(
                        text = T("ls_Income",currentLanguage),
                        style = AtasuaiTheme.typography.ProposalNameStyle
                    )

                }
            }
        }
        Column(modifier=Modifier.weight(1f)
            .background(AtasuaiTheme.colors.background)
            .padding(horizontal = responsiveWidth(20f))
        ){
            EmptyNotification(currentLanguage,modifier=Modifier.fillMaxWidth(),NotifiType.Income)
        }
    }
}
