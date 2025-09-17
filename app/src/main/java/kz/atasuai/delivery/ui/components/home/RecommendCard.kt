package kz.atasuai.delivery.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.theme.ProposalIdStyle
import kz.atasuai.delivery.ui.theme.ProposalTimeStyle

@Composable
fun RecommendCard(modifier: Modifier,onClick:(Int)-> Unit){
    val priceStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 16.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = Color(0xFF17A34A),

        )
    Column(modifier = Modifier.fillMaxWidth()
        .noRippleClickable {
            onClick(1)
        }
        .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 18.dp))
        .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 18.dp))
        .padding(18.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(
                text = "Тапсырыс #A7D3",
                style = AtasuaiTheme.typography.ProposalIdStyle
            )
            Text(
                text = "25,000₸",
                style = priceStyle
            )
        }
        VSpacerHi(25f)
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Row(
                    modifier=Modifier.wrapContentSize()
                        .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 40.dp))
                        .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 40.dp))
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.box_icon),
                        contentDescription = "box",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(13.dp)
                    )
                    VSpacerWi(4f)
                    Text(
                        text = "21 зат",
                        style = AtasuaiTheme.typography.ProposalTimeStyle
                    )
                }
                VSpacerWi(4f)
                Row(
                    modifier=Modifier.wrapContentSize()
                        .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 40.dp))
                        .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 40.dp))
                        .padding(horizontal = 10.dp, vertical = 7.dp)
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.location_icon),
                        contentDescription = "box",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(13.dp)
                    )
                    VSpacerWi(4f)
                    Text(
                        text = "15 мекенжай",
                        style = AtasuaiTheme.typography.ProposalTimeStyle
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Толығырақ",
                    style = AtasuaiTheme.typography.ProposalTimeStyle
                )
                VSpacerWi(2f)
                Icon(
                    painter = painterResource(id = R.drawable.right_shoulder),
                    contentDescription = "box",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(10.dp)
                )
            }

        }
    }
}