package kz.atasuai.delivery.ui.components.regis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue


@Composable
fun PageProgressIndicator(
    currentPage: Int,
    totalPages: Int,
    pageOffset: Float = 0f,
    modifier: Modifier = Modifier,
    isTransparent: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalPages) { index ->
            val progress = when {
                index < currentPage -> 1f
                index == currentPage -> {
                    1f - pageOffset.absoluteValue
                }
                else -> 0f
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(Color.Gray.copy(0.3f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .background(
                           color = if(isTransparent) Color(0xFF1473FA) else Color(0xFFFF6B35),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}

