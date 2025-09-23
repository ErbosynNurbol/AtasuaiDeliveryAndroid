package kz.atasuai.delivery.ui.components.qr


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
import androidx.compose.ui.graphics.Brush
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
fun QRTypeSwitch(
    isOnline: Boolean,
    onStatusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    currentLanguage:LanguageModel,
    darkTheme: Boolean,
) {
    val switchTextCo1 = if(!isOnline) Color(0xFF353535) else Color(0xFFFFFFFF)
    val switchTextCo2 = if(isOnline) Color(0xFF353535) else Color(0xFFFFFFFF)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(43.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x33FFFFFF).copy(alpha = 0.25f),
                        Color(0x33FFFFFF).copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(53.dp)
            )
            .border(
                width = 0.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.4f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(53.dp)
            )
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
                        shape = RoundedCornerShape(53.dp)
                    )

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR сканерлеу",
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
                        shape = RoundedCornerShape(53.dp)
                    )

                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR көрсету",
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



@Composable
fun QRShowSwitch(
    isOnline: Boolean,
    onStatusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    currentLanguage: LanguageModel,
    darkTheme: Boolean,
) {
    val switchTextCo1 = if(isOnline) Color(0xFF353535) else Color(0xFFFFFFFF)
    val switchTextCo2 = if(!isOnline) Color(0xFF353535) else Color(0xFFFFFFFF)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(43.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x33FFFFFF).copy(alpha = 0.25f),
                        Color(0x33FFFFFF).copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(53.dp)
            )
            // 玻璃效果边框
            .border(
                width = 0.5.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.4f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(53.dp)
            )
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
                        color = if (isOnline) Color(0xFF4F89FC) else Color.Transparent,
                        shape = RoundedCornerShape(53.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR сканерлеу",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = switchTextCo2,
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight()
                    .background(
                        color = if (!isOnline) Color(0xFF4F89FC) else Color.Transparent,
                        shape = RoundedCornerShape(53.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR көрсету",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = switchTextCo1,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
    }
}