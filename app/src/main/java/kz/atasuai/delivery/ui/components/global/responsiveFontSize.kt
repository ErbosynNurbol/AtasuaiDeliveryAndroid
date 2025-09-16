package kz.atasuai.delivery.ui.components.global

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun responsiveFontSize(baseSp: Float): TextUnit {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.toFloat()

    val scaleFactor = when {
        screenWidthDp < 360 -> 0.9f
        screenWidthDp < 480 -> 1.0f
        screenWidthDp < 600 -> 1.1f
        else -> 1.5f
    }
    return (baseSp * scaleFactor).sp
}