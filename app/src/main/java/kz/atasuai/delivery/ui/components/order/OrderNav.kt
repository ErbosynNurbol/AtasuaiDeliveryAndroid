package kz.atasuai.delivery.ui.components.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.market.models.LanguageModel

@Composable
fun OrderNav(currentLanguage:LanguageModel,modifier: Modifier){
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ){
        TitleStyle(
            text = "Қазір процессте",
            fontSize = 18f, fontWeight = 600
        )
        Row(modifier = Modifier.wrapContentSize()
            .border(1.dp,AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(30.dp))
            .background(Color.White,shape = RoundedCornerShape(30.dp))
            .padding(top = 5.dp, end = 15.dp, start = 5.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
          Icon(
              painter = painterResource(id = R.drawable.online_order),
              contentDescription = "online order",
              tint = Color.Unspecified,
              modifier = Modifier.size(20.dp)
          )
            VSpacerWi(5f)
            TitleStyle(
                text = "Онлайн",
                fontSize = 12f, fontWeight = 400
            )
        }
    }
}