package kz.atasuai.delivery.common

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.common.ThemeManager
import kz.atasuai.market.common.ThemeMode
import kz.atasuai.market.models.LanguageModel


@Composable
fun ThemeSelectionButtons(context: Context, currentLanguage: LanguageModel,themeManager: ThemeManager) {

    val currentThemeMode by themeManager.themeMode.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThemeSelectionButton(
            text = T("ls_Auto", currentLanguage),
            isSelected = currentThemeMode == ThemeMode.FOLLOW_SYSTEM,
            onClick = { themeManager.updateThemeMode(ThemeMode.FOLLOW_SYSTEM)
                (context as? Activity)?.recreate()
            }
        )

        ThemeSelectionButton(
            text = T("ls_Light", currentLanguage),
            isSelected = currentThemeMode == ThemeMode.LIGHT,
            onClick = { themeManager.updateThemeMode(ThemeMode.LIGHT)
                (context as? Activity)?.recreate()
            }
        )

        ThemeSelectionButton(
            text = T("ls_Dark", currentLanguage),
            isSelected = currentThemeMode == ThemeMode.DARK,
            onClick = {
                themeManager.updateThemeMode(ThemeMode.DARK)

                (context as? Activity)?.recreate()

            }
        )
    }
}

@Composable
private fun ThemeSelectionButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFF58A0C8)
    } else {
        Color.Transparent
    }
val textColor = if (isSelected) {
    Color(0xFFFFFFFF)
} else {
   AtasuaiTheme.colors.textSecondary
}
    val borderColor = if (isSelected) {
        Color.Transparent
    }else{
        AtasuaiTheme.colors.textTertiary
    }
    Box(
        modifier = Modifier
            .wrapContentSize()
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(size = 5.dp))
            .background(backgroundColor, shape = RoundedCornerShape(size = 5.dp))
            .padding(10.dp)
        .clickable
            (
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
                    )
        { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                fontFamily = PrimaryFontFamily,
                fontWeight = FontWeight(500),
                color = textColor,
            )
        )
    }
}