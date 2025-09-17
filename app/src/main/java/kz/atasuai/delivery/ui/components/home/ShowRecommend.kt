package kz.atasuai.delivery.ui.components.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.SpanStyle
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.theme.ProposalNameStyle
import kz.atasuai.market.models.LanguageModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ShowRecommend(currentLanguage: LanguageModel,
                  onDismissRequest: () -> Unit,){
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.padding(0.dp),
                width = 65.dp,
                height = 5.dp,
                color = colorResource(id = R.color.switch_bac_co),
                shape = RoundedCornerShape(10.dp)
            )
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(screenHeight * 0.8f)
        ) {
            Column(modifier = Modifier.weight(0.85f)){
                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier.fillMaxSize()
                ){
                    item{
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                            ){
                            Text(
                                text = "Тапсырыс #A7D3",
                                style = AtasuaiTheme.typography.ProposalNameStyle
                            )
                            Text(
                                text = "25,000₸",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 20.sp,
                                    fontFamily = PrimaryFontFamily,
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF17A34A),
                                )
                            )

                        }
                        VSpacerHi(28f)
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                            ){
                            Row(modifier = Modifier.weight(0.5f)){
                                SpanStyle(text = T("ls_Today",currentLanguage))
                                VSpacerWi(10f)
                                TitleStyle(text = "12.09.2025", fontSize = 14f)
                            }
                            Row(modifier = Modifier.weight(0.5f)){
                                SpanStyle(text = T("ls_Tomorrow",currentLanguage))
                                VSpacerWi(10f)
                                TitleStyle(text = "13.09.2025", fontSize = 14f)
                            }
                        }
                        VSpacerHi(15f)
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(modifier = Modifier.weight(0.5f)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color(0xFFE5E7EB),
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(5f, 5f), 0f
                                            )
                                        ),
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                }
                                .background(color = Color(0xFFF6F7FB), shape = RoundedCornerShape(size = 10.dp))
                                .padding(13.dp)
                            ){
                                SpanStyle(text = "\uD83D\uDD51 Қабылдап алу", fontSize = 12f)
                                VSpacerHi(7f)
                                TitleStyle(text = "12:00 дейін", fontSize = 14f)
                            }
                            VSpacerWi(6f)
                            Column(modifier = Modifier.weight(0.5f)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color(0xFFE5E7EB),
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(5f, 5f), 0f
                                            )
                                        ),
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                }
                                .background(color = Color(0xFFF6F7FB), shape = RoundedCornerShape(size = 10.dp))
                                .padding(13.dp)
                            ){
                                SpanStyle(text = "\uD83D\uDD51 Жеткізу уақыты", fontSize = 12f)
                                VSpacerHi(7f)
                                TitleStyle(text = "20:00 дейін", fontSize = 14f)
                            }
                        }
                    }
                    item{
                        VSpacerHi(28f)
                        TitleStyle(
                            text = "Толық маршрут",
                            fontSize = 14f, fontWeight =600
                        )
                        VSpacerHi(16f)
                        Row(modifier = Modifier.fillMaxWidth()
                            .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 18.dp))
                            .padding(14.dp)
                        ){
                            Box(modifier = Modifier
                                .wrapContentSize()
                                .heightIn(38.dp)
                                .background(color = Color(0xFFF8F9FA), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 16.dp)
                                ,
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "1",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 17.6.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF1D2630),
                                    )
                                )
                            }
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
                                                .background(color = Color(0xFF17A34A), shape = RoundedCornerShape(size = 30.dp))
                                        ){}
                                        VSpacerHi(3f)
                                        Box(Modifier
                                            .border(width = 1.dp, color = Color(0xFFD3DCE6))
                                            .padding(1.dp)
                                            .width(0.dp)
                                            .height(10.dp)
                                        ){}
                                        VSpacerHi(3f)
                                        Box(
                                            Modifier
                                                .width(8.dp)
                                                .height(8.dp)
                                                .background(color = Color(0xFF0CA5E9), shape = RoundedCornerShape(size = 30.dp))
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
                                    Box(Modifier
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
                        VSpacerHi(8f)
                        Row(modifier = Modifier.fillMaxWidth()
                            .border(width = 1.dp, color = AtasuaiTheme.colors.cardBorderCo, shape = RoundedCornerShape(size = 18.dp))
                            .padding(14.dp)
                        ){
                            Box(modifier = Modifier
                                .wrapContentSize()
                                .heightIn(38.dp)
                                .background(color = Color(0xFFF8F9FA), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 16.dp)
                                ,
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "2",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 17.6.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF1D2630),
                                    )
                                )
                            }
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
                                                .background(color = Color(0xFF17A34A), shape = RoundedCornerShape(size = 30.dp))
                                        ){}
                                        VSpacerHi(3f)
                                        Box(Modifier
                                            .border(width = 1.dp, color = Color(0xFFD3DCE6))
                                            .padding(1.dp)
                                            .width(0.dp)
                                            .height(10.dp)
                                        ){}
                                        VSpacerHi(3f)
                                        Box(
                                            Modifier
                                                .width(8.dp)
                                                .height(8.dp)
                                                .background(color = Color(0xFF0CA5E9), shape = RoundedCornerShape(size = 30.dp))
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
                                    Box(Modifier
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

                    }
                    item{
                        VSpacerHi(28f)
                        TitleStyle(
                            text = "Қосымша ақпарат",
                            fontSize = 14f, fontWeight =600
                        )
                        VSpacerHi(14f)
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Column(modifier = Modifier.weight(0.5f)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color(0xFFE5E7EB),
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(5f, 5f), 0f
                                            )
                                        ),
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                }
                                .background(color = Color(0xFFF6F7FB), shape = RoundedCornerShape(size = 10.dp))
                                .padding(13.dp)
                            ){
                                SpanStyle(text = "\uD83D\uDD52 Жалпы уақыт", fontSize = 12f)
                                VSpacerHi(7f)
                                TitleStyle(text = "6-8 сағат", fontSize = 14f)
                            }
                            VSpacerWi(6f)
                            Column(modifier = Modifier.weight(0.5f)
                                .drawBehind {
                                    drawRoundRect(
                                        color = Color(0xFFE5E7EB),
                                        style = Stroke(
                                            width = 2.dp.toPx(),
                                            pathEffect = PathEffect.dashPathEffect(
                                                floatArrayOf(5f, 5f), 0f
                                            )
                                        ),
                                        cornerRadius = CornerRadius(10.dp.toPx())
                                    )
                                }
                                .background(color = Color(0xFFF6F7FB), shape = RoundedCornerShape(size = 10.dp))
                                .padding(13.dp)
                            ){
                                SpanStyle(text = "\uD83D\uDE97 Барлық қашықтық", fontSize = 12f)
                                VSpacerHi(7f)
                                TitleStyle(text = "20:00 дейін", fontSize = 14f)
                            }
                        }

                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(0.15f),
                verticalAlignment = Alignment.CenterVertically
            ){
                GlobalButton(
                    text = T("ls_Accept",currentLanguage),
                    loadingText = T("ls_Loading",currentLanguage),
                    status = ButtonStatus.Enabled,
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}