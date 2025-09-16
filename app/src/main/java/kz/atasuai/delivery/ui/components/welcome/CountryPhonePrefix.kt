package kz.atasuai.market.ui.components.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.responsiveWidth


@Composable
fun CountryPhonePrefix(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(width = 1.dp, color = Color(0xFFE8EBF1),shape = RoundedCornerShape(10.dp))
            .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(40.dp))
            .size(responsiveWidth(50f)),
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
        }
    }
}
