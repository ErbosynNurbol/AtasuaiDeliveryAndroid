package kz.atasuai.delivery.ui.components.home


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp.Companion.currentLanguage
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel

@Composable
fun OnlineOfflineSwitch(
    isOnline: Boolean,
    onStatusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    currentLanguage:LanguageModel
) {
    val switchTextCo1 = if(!isOnline) Color(0xFF484C52) else Color(0xFFFFFFFF)
    val switchTextCo2 = if(isOnline) Color(0xFF484C52) else Color(0xFFFFFFFF)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(width = 1.dp, color = Color(0xFFE7E7E7), shape = RoundedCornerShape(50.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onStatusChange(!isOnline) }
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(
                        color = if (!isOnline) Color(0xFFEF4444) else Color(0xFFFFFFFF),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = T("ls_Offline", currentLanguage),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = switchTextCo2,
                        textAlign =  TextAlign.Center,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(
                        color = if (isOnline) Color(0xFF29BE10) else Color(0xFFFFFFFF),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = T("ls_Online", currentLanguage),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = switchTextCo1,
                        textAlign =  TextAlign.Center,
                    )
                )
            }
        }
    }
}