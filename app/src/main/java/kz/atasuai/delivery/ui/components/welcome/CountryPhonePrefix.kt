package kz.atasuai.market.ui.components.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@Composable
fun CountryPhonePrefix(
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(
        fontSize = responsiveFontSize(14f),
        lineHeight = responsiveFontSize(14f),
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(500),
        color = AtasuaiTheme.colors.textPrimary,
    )
    Box(
        modifier = modifier
            .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo,shape = RoundedCornerShape(10.dp))
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(40.dp))
            .height(responsiveWidth(50f))
            .wrapContentWidth()
            .padding(horizontal = 10.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(currentLanguage.flagUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = "Flag",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(12.5.dp))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "+7",
                style = textStyle
            )
        }
    }
}
