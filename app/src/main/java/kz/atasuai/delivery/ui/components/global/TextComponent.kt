package kz.atasuai.delivery.ui.components.global

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@Composable
fun TitleStyle(text:String, fontSize: Float = 20f, fontWeight: Int = 600,
               color: Color = AtasuaiTheme.colors.textPrimary,
               maxLines:Int = 1,
               modifier: Modifier=Modifier
             ){
    Text(
        text = text,
        style = TextStyle(
            fontSize =responsiveFontSize(fontSize),
            lineHeight = responsiveFontSize(fontSize),
            fontFamily = PrimaryFontFamily,
            fontWeight = FontWeight(fontWeight),
            color = color,
        ),
        modifier = modifier,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun SpanStyle(text:String, fontSize: Float = 11f, fontWeight: Int = 400,
              color: Color = AtasuaiTheme.colors.textSecondary,
              modifier: Modifier=Modifier
){
    Text(
        text = text,
        style = TextStyle(
            fontSize =responsiveFontSize(fontSize),
            lineHeight = responsiveFontSize(fontSize),
            fontFamily = PrimaryFontFamily,
            fontWeight = FontWeight(fontWeight),
            color = color,
        ),
        modifier = modifier
    )
}

@Composable
fun ActivityTitle(text:String, fontSize: Float = 20f, fontWeight: Int = 600,
                  color: Color = AtasuaiTheme.colors.textTertiary,
                  modifier: Modifier=Modifier){
    Text(
        text = text,
        style = TextStyle(
            fontSize = responsiveFontSize(fontSize),
            lineHeight = responsiveFontSize(fontSize),
            fontFamily = PrimaryFontFamily,
            fontWeight = FontWeight(fontWeight),
            color = color,
        ),
        modifier = modifier
    )
}