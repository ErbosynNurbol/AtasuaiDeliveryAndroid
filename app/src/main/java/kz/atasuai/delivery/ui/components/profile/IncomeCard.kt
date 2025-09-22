package kz.atasuai.delivery.ui.components.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel

@Composable
fun IncomeCard(
    currentLanguage: LanguageModel,
    context: Context,
    modifier: Modifier,
){
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
        ){
        Column(modifier=Modifier.weight(0.3f)
            .height(124.dp)
            .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(20.dp))
            ){
            Text(
                text = "Бүгін",
                style = TextStyle(
                    fontSize = responsiveFontSize(11f),
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.recommendTimeCo,
                ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TitleStyle(
                text = "24000 ₸", fontSize = 17f,modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
                ){
                Image(
                    painter = painterResource(id = R.drawable.income_box_today),
                    contentDescription = "income",
                    modifier = Modifier.size(76.dp)
                )
            }

        }
        VSpacerWi(7f)
        Column(modifier=Modifier.weight(0.3f)
            .height(124.dp)
            .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(20.dp))
        ){
            Text(
                text = "Бүгін",
                style = TextStyle(
                    fontSize = responsiveFontSize(11f),
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.recommendTimeCo,
                ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TitleStyle(
                text = "24000 ₸", fontSize = 17f,modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Image(
                    painter = painterResource(id = R.drawable.income_box_today),
                    contentDescription = "income",
                    modifier = Modifier.size(76.dp)
                )
            }

        }
        VSpacerWi(7f)
        Column(modifier=Modifier.weight(0.3f)
            .height(124.dp)
            .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(20.dp))
        ){
            Text(
                text = "Бүгін",
                style = TextStyle(
                    fontSize = responsiveFontSize(11f),
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.recommendTimeCo,
                ),
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TitleStyle(
                text = "24000 ₸", fontSize = 17f,modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Row(modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Image(
                    painter = painterResource(id = R.drawable.income_box_today),
                    contentDescription = "income",
                    modifier = Modifier.size(76.dp)
                )
            }

        }
    }

}