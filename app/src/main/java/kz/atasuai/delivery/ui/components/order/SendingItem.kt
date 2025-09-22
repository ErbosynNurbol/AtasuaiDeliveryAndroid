package kz.atasuai.delivery.ui.components.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily

@Composable
fun SendingItem(){
    val gradients = Brush.horizontalGradient(listOf(Color(0xFF75B3FF),PrimaryColor))
    Column(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(brush = gradients, shape = RoundedCornerShape(18.dp))
        .padding(14.dp)
    ){
        Row(modifier = Modifier.fillMaxWidth()){
            Icon(
                painter = painterResource(id = R.drawable.sending_car_icon),
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
                        Box(Modifier
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
                        Text(
                            text = "Райымбек батыр 32/3 (ЖК Алатау)",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 13.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFFFFFFF),
                            )
                        )
                        VSpacerHi(8f)
                        Text(
                            text = "Әл-Фараби 45/2 (Бизнес центр Time)",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 13.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(400),
                                color = Color(0xFFFFFFFF),
                            )
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
                            fontWeight = FontWeight(500),
                            color = Color.White,
                        )
                    )
                    VSpacerWi(8f)
                    Box(Modifier
                        .width(4.dp)
                        .height(4.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(size = 30.dp)))
                    VSpacerWi(8f)
                    Text(
                        text = "3,5 km",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color.White,
                        )
                    )
                }
            }
        }
        VSpacerHi(18f)
        Row(modifier = Modifier.fillMaxWidth()){
            Box(modifier = Modifier.weight(0.5f)
                .wrapContentHeight()
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
                .padding(vertical = 13.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Маршрутқа өту",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF4F89FC),
                    )
                )
            }
            VSpacerWi(6f)
            Box(modifier = Modifier.weight(0.5f)
                .wrapContentHeight()
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 10.dp))
                .padding(vertical = 13.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Келіп жеттім",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 13.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF4F89FC),
                    )
                )
            }
        }
    }

}