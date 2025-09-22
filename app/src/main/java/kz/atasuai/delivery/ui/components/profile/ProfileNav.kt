package kz.atasuai.delivery.ui.components.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel

@Composable
fun ProfileNav(
    currentLanguage: LanguageModel,
    context: Context,
    modifier: Modifier,
){
  Row(modifier = modifier,
      verticalAlignment = Alignment.CenterVertically
      ){
    Image(
        painter = painterResource(id = R.drawable.avatar),
        contentDescription = "avatar",
        modifier = Modifier.size(63.dp)
            .clip(CircleShape)
        ,
        contentScale = ContentScale.Crop
    )
      VSpacerWi(10f)
      Column(modifier = Modifier.weight(1f)){
          Row(modifier=Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
              ){
              TitleStyle(
                  text = "Даң даң нәби", fontSize = 22f
              )
              VSpacerWi(4f)
              Icon(
                  painter = painterResource(id = R.drawable.profile_right_icon),
                  contentDescription = "to",
                  tint = Color.Unspecified,
                  modifier = Modifier.size(16.dp)
              )
          }
          VSpacerHi(8f)
          Text(
              text = "+7 708 345 3242",
              style = TextStyle(
                  fontSize = 14.sp,
                  lineHeight = 16.8.sp,
                  fontFamily = PrimaryFontFamily,
                  fontWeight = FontWeight(400),
                  color = AtasuaiTheme.colors.emptyDesCo,
              )
          )
      }
  }
}