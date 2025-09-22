package kz.atasuai.delivery.ui.components.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily

@Composable
fun ZholaiItem(){
    Column(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 18.dp))
        .background(color = Color.White, shape = RoundedCornerShape(18.dp))
        .padding(14.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth()){
            Icon(
                painter = painterResource(id = R.drawable.zholai_box_icon),
                contentDescription = "car",
                tint = Color.Unspecified,
                modifier = Modifier.size(34.dp)
            )
            VSpacerWi(14f)
            Column(
                modifier = Modifier.weight(1f)
            ){
                Row(){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Box(
                            Modifier
                                .width(8.dp)
                                .height(8.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
                        ){}
                        VSpacerHi(3f)
                        Box(
                            Modifier
                            .border(width = 1.dp, color = Color.White)
                            .padding(1.dp)
                            .width(0.dp)
                            .height(10.dp)
                        ){}
                        VSpacerHi(3f)
                        Box(
                            Modifier
                                .width(8.dp)
                                .height(8.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp))
                        ){}
                    }
                    VSpacerWi(7f)
                    Column(){
                        TitleStyle(
                            text = "Райымбек батыр 32/3 (ЖК Алатау)",
                            fontSize = 13f, fontWeight = 600
                        )
                        VSpacerHi(8f)
                        TitleStyle(
                            text = "Әл-Фараби 45/2 (Бизнес центр Time)",
                            fontSize = 13f, fontWeight = 400
                        )
                    }
                }
                VSpacerHi(16f)
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "15 min",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFF6B7280),
                        )
                    )
                    VSpacerWi(8f)
                    Box(
                        Modifier
                            .width(4.dp)
                            .height(4.dp)
                            .background(color = Color(0xFF6B7280), shape = RoundedCornerShape(size = 30.dp)))
                    VSpacerWi(8f)
                    Text(
                        text = "3,5 km",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF6B7280),
                        )
                    )
                }
            }
        }
        VSpacerHi(18f)
        Row(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier.weight(0.3f)
                .wrapContentHeight()
                .background(color = Color(0xFF17A34A), shape = RoundedCornerShape(size = 10.dp))
                .padding(vertical = 13.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Қабылдау",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                    )
                )
            }
            VSpacerWi(6f)
            Box(modifier = Modifier.weight(0.3f)
                .wrapContentHeight()
                .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 10.dp))
                .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 10.dp))
                .padding(vertical = 13.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Бас тарту",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF0A1220),
                    )
                )
            }
            Row(modifier = Modifier.weight(0.4f)
                .wrapContentHeight()
                .padding(vertical = 13.dp),
               horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Толығырақ",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = AtasuaiTheme.colors.textTertiary,
                    )
                )
                VSpacerWi(4f)
                Icon(
                    painter = painterResource(id = R.drawable.right_shoulder),
                    contentDescription = "right",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}