package kz.atasuai.market.ui.components.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp


@Composable
fun PasswordStrengthIndicator(strength: Int, modifier: Modifier = Modifier) {
    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(Color.White, shape = RoundedCornerShape(2.5.dp))
                .clip(RoundedCornerShape(2.5.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(strength / 100f)
                    .fillMaxHeight()
                    .background(
                        when {
                            strength <= 25 -> Color(0xFFE8503A)  // 弱密码用红色
                            strength in 26..50 -> Color(0xFFFF9800) // 中等密码用橙色
                            else -> Color(0xFF29BE10)  // 强密码用绿色
                        },
                        shape = RoundedCornerShape(2.5.dp)
                    )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = when {
                strength <= 25 -> T("ls_Toofewletters", currentLanguage)
                strength in 26..50 -> T("ls_Medium", currentLanguage)
                else -> T("ls_Strong password", currentLanguage)
            },
            fontSize = 8.sp,
            lineHeight = 11.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(300),
            color = when {
                strength <= 25 -> Color(0xFFE8503A)  // 弱密码用红色
                strength in 26..50 -> Color(0xFFFF9800) // 中等密码用橙色
                else -> Color(0xFF29BE10)  // 强密码用绿色
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(22.dp)
        )
    }
}
