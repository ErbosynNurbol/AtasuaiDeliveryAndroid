package kz.atasuai.delivery.common

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class GradientDirection {
    TopToBottom,
    BottomToTop,
    LeftToRight,
    RightToLeft
}

fun createCustomGradientBrush(
    color: Color,
    startOffset: Offset,
    endOffset: Offset,
    alphaStart: Float = 1.0f,
    alphaEnd: Float = 0.0f
): Brush {
    val startColor = color.copy(alpha = alphaStart)
    val endColor = color.copy(alpha = alphaEnd)

    return Brush.linearGradient(
        colors = listOf(startColor, endColor),
        start = startOffset,
        end = endOffset
    )
}



//val brush = createCustomGradientBrush(
//    color = Color.Green,
//    startOffset = Offset(0f, 0f),        // 左边
//    endOffset = Offset(Float.POSITIVE_INFINITY, 0f), // 右边
//    alphaStart = 1.0f,
//    alphaEnd = 0.0f
//)