package kz.atasuai.delivery.ui.components.order


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
import androidx.compose.ui.draw.shadow
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
fun OrderTypeSwitch(
    isOnline: Boolean,
    onStatusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    currentLanguage:LanguageModel,
    darkTheme: Boolean,
) {
    val switchTextCo1 = Color(0xFF585C5E)
    val switchTextCo2 = Color(0xFF585C5E)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(43.dp)
            .background(Color(0xFFE9EAF2), shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color(0xFFE7E7E7), shape = RoundedCornerShape(50.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onStatusChange(!isOnline) }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(0.5f)
                    .fillMaxHeight()
                    .background(
                        color = if (isOnline) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Жоспарлы",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
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
                        color = if (!isOnline) Color.White else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Жолай",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = switchTextCo1,
                        textAlign =  TextAlign.Center,
                    )
                )
            }
        }
    }
}