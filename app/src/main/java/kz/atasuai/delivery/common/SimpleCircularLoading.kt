package kz.atasuai.delivery.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme


@Composable
fun CircularBarsLoading(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    color: Color = PrimaryColor,
    background:Color = AtasuaiTheme.colors.background,
    secondaryColor: Color = color.copy(alpha = 0.3f),
    strokeWidth: Dp = 4.dp,
    durationMillis: Int = 1500
) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcLength by infiniteTransition.animateFloat(
        initialValue = 60f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val arcStart by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = modifier.fillMaxSize( )
            .background(background)
        ,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
                .padding(strokeWidth / 2)
        ) {
            // 绘制背景圆环
            drawArc(
                color = secondaryColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // 绘制旋转的进度弧
            drawArc(
                color = color,
                startAngle = arcStart,
                sweepAngle = arcLength,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}