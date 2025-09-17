package kz.atasuai.delivery.ui.components.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel


@Composable
fun WelcomeModal(
    onDismissRequest: () -> Unit,
             context: Context,
    currentLanguage: LanguageModel
){
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .width(331.dp)
                .wrapContentHeight()
                .heightIn(160.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.welcome_icon),
                contentDescription = "welcome",
                modifier = Modifier.width(208.dp).height(138.dp)
            )
            VSpacerHi(28f)
            Text(
                text = "Қош келдіңіз!",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF181D27),
                    textAlign = TextAlign.Center,
                )
            )
            VSpacerHi(10f)
            Text(
                text = "Жеткізуді бастау үшін толық тіркеуден өту қажет",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(300),
                    color = Color(0xFF7B7E90),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .width(184.dp)
            )
            VSpacerHi(16f)
            GlobalButton(
                text = T("ls_Register2", currentLanguage),
                status = ButtonStatus.Enabled,
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            )
        }
    }
}