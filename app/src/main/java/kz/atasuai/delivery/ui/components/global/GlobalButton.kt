package kz.atasuai.delivery.ui.components.global

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@Composable
fun GlobalButton(
    text: String,
    containerColor:Color = PrimaryColor,
    loadingText: String = "",
    status: ButtonStatus,
    fontSize:Int=18,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = if (status == ButtonStatus.Enabled) {
        Color.White
    } else {
        Color.White.copy(alpha = 0.6f)
    }
    Button(
        onClick = {
            if (status == ButtonStatus.Enabled)
            {
                onClick()
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor =containerColor.copy(alpha = 0.3f),
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = status == ButtonStatus.Enabled
    ) {
        when (status) {
            ButtonStatus.Loading -> {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = textColor,
                        modifier = Modifier.size(24.dp)
                    )
                    if (loadingText.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = loadingText,
                            style = TextStyle(
                                fontSize = fontSize.sp,
                                lineHeight = 18.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(500),
                                color = textColor,

                                )
                        )
                    }
                }
            }
            else -> {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = fontSize.sp,
                        lineHeight = 18.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = textColor,
                        )
                )
            }
        }
    }
}
