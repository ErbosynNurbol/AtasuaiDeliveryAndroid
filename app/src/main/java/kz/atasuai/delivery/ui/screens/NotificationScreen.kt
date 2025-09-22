package kz.atasuai.delivery.ui.screens

import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveFontSize
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.theme.ProposalNameStyle
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.notification.NotificationViewModel
import kz.atasuai.market.models.LanguageModel
import kotlin.math.roundToInt

@Composable
fun NotificationScreen(
    context: Context,
    darkTheme: Boolean,
    viewModel: NotificationViewModel,
    currentLanguage: LanguageModel
){

    Column(modifier = Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.welcomeBac)
    ){
        VSpacerHi(54f)
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = responsiveWidth(20f))
        ){
            Text(
                text = "Хабарламалар",
                style = AtasuaiTheme.typography.ProposalNameStyle
            )
        }
        VSpacerHi(46f)
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ){

            item{
                val coroutineScope = rememberCoroutineScope()
                val swipeOffsetX = remember { Animatable(0f) }
                val buttonWidth = 80.dp
                val maxSwipeDistance = with(LocalDensity.current) { (buttonWidth * 2).toPx() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = responsiveWidth(if (swipeOffsetX.value > -10f) 20f else 0f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-maxSwipeDistance, 0f)
                                    coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        if (swipeOffsetX.value < -maxSwipeDistance / 3) {
                                            swipeOffsetX.animateTo(-maxSwipeDistance)
                                        } else {
                                            swipeOffsetX.animateTo(0f)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    if (swipeOffsetX.value < -10f) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFF4F6FD), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.un_notification),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF0775CA),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFFCEAEC), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_icon),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFEE4956),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .padding(end = if (swipeOffsetX.value < -10f) 90.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onHorizontalDrag = { _, dragAmount ->
                                            val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-160f, 0f)
                                            coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                if (swipeOffsetX.value < -50f) {
                                                    swipeOffsetX.animateTo(-160f)
                                                } else {
                                                    swipeOffsetX.animateTo(0f)
                                                }
                                            }
                                        }
                                    )
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.notifi_order_icon),
                                contentDescription = "order",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(56.dp)
                            )
                            VSpacerWi(12f)
                            Column(modifier = Modifier.weight(1f)){
                                TitleStyle(
                                    text = "Тапсырыстар",
                                    fontSize = 16f, fontWeight = 600
                                )
                                VSpacerHi(7f)
                                Text(
                                    text = "Наурызбай ауданы бойынша жаңа тапсырыстар тізбегі",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                            }
                            VSpacerWi(12f)
                            Column(){
                                Text(
                                    text = "15:25",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        lineHeight = 11.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                                VSpacerHi(10f)
                                Box(modifier = Modifier.size(24.dp)
                                    .background(color = Color(0xFF4F89FC), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "1",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.2.sp,
                                            fontFamily = PrimaryFontFamily  ,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
            }
            item{
                VSpacerHi(16f)
                val coroutineScope = rememberCoroutineScope()
                val swipeOffsetX = remember { Animatable(0f) }
                val buttonWidth = 80.dp
                val maxSwipeDistance = with(LocalDensity.current) { (buttonWidth * 2).toPx() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = responsiveWidth(if (swipeOffsetX.value > -10f) 20f else 0f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-maxSwipeDistance, 0f)
                                    coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        if (swipeOffsetX.value < -maxSwipeDistance / 3) {
                                            swipeOffsetX.animateTo(-maxSwipeDistance)
                                        } else {
                                            swipeOffsetX.animateTo(0f)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    if (swipeOffsetX.value < -10f) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFF4F6FD), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.un_notification),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF0775CA),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFFCEAEC), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_icon),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFEE4956),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .padding(end = if (swipeOffsetX.value < -10f) 90.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onHorizontalDrag = { _, dragAmount ->
                                            val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-160f, 0f)
                                            coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                if (swipeOffsetX.value < -50f) {
                                                    swipeOffsetX.animateTo(-160f)
                                                } else {
                                                    swipeOffsetX.animateTo(0f)
                                                }
                                            }
                                        }
                                    )
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.score_icon),
                                contentDescription = "order",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(56.dp)
                            )
                            VSpacerWi(12f)
                            Column(modifier = Modifier.weight(1f)){
                                TitleStyle(
                                    text = "Табыс",
                                    fontSize = 16f, fontWeight = 600
                                )
                                VSpacerHi(7f)
                                Text(
                                    text = "Сіз ₸1 500 таптыңыз (+₸300 шайпұл)",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                            }
                            VSpacerWi(12f)
                            Column(){
                                Text(
                                    text = "15:25",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        lineHeight = 11.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                                VSpacerHi(10f)
                                Box(modifier = Modifier.size(24.dp)
                                    .background(color = Color(0xFF4F89FC), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "2",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.2.sp,
                                            fontFamily = PrimaryFontFamily  ,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
            }
            item{
                VSpacerHi(16f)
                val coroutineScope = rememberCoroutineScope()
                val swipeOffsetX = remember { Animatable(0f) }
                val buttonWidth = 80.dp
                val maxSwipeDistance = with(LocalDensity.current) { (buttonWidth * 2).toPx() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = responsiveWidth(if (swipeOffsetX.value > -10f) 20f else 0f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-maxSwipeDistance, 0f)
                                    coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        if (swipeOffsetX.value < -maxSwipeDistance / 3) {
                                            swipeOffsetX.animateTo(-maxSwipeDistance)
                                        } else {
                                            swipeOffsetX.animateTo(0f)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    if (swipeOffsetX.value < -10f) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFF4F6FD), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.un_notification),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF0775CA),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFFCEAEC), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_icon),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFEE4956),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .padding(end = if (swipeOffsetX.value < -10f) 90.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onHorizontalDrag = { _, dragAmount ->
                                            val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-160f, 0f)
                                            coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                if (swipeOffsetX.value < -50f) {
                                                    swipeOffsetX.animateTo(-160f)
                                                } else {
                                                    swipeOffsetX.animateTo(0f)
                                                }
                                            }
                                        }
                                    )
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.system_icon),
                                contentDescription = "order",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(56.dp)
                            )
                            VSpacerWi(12f)
                            Column(modifier = Modifier.weight(1f)){
                                TitleStyle(
                                    text = "Система",
                                    fontSize = 16f, fontWeight = 600
                                )
                                VSpacerHi(7f)
                                Text(
                                    text = "Қолданба v1.8.2 сканер қателерін түзетеді. Ауысымға дейін жаңартыңыз.",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                            }
                            VSpacerWi(12f)
                            Column(){
                                Text(
                                    text = "15:25",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        lineHeight = 11.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                                VSpacerHi(10f)
                                Box(modifier = Modifier.size(24.dp)
                                    .background(color = Color(0xFF4F89FC), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "3",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.2.sp,
                                            fontFamily = PrimaryFontFamily  ,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
            }
            item{
                VSpacerHi(16f)
                val coroutineScope = rememberCoroutineScope()
                val swipeOffsetX = remember { Animatable(0f) }
                val buttonWidth = 80.dp
                val maxSwipeDistance = with(LocalDensity.current) { (buttonWidth * 2).toPx() }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = responsiveWidth(if (swipeOffsetX.value > -10f) 20f else 0f))
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { _, dragAmount ->
                                    val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-maxSwipeDistance, 0f)
                                    coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        if (swipeOffsetX.value < -maxSwipeDistance / 3) {
                                            swipeOffsetX.animateTo(-maxSwipeDistance)
                                        } else {
                                            swipeOffsetX.animateTo(0f)
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    if (swipeOffsetX.value < -10f) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFF4F6FD), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.un_notification),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFF0775CA),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                            Column(modifier = Modifier.wrapContentSize()
                                .background(color = Color(0xFFFCEAEC), shape = RoundedCornerShape(size = 10.dp))
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_icon),
                                    contentDescription = "un",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                                VSpacerHi(5f)
                                Text(
                                    text = "Өшіру",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        lineHeight = 13.2.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = Color(0xFFEE4956),
                                        textAlign = TextAlign.Center,
                                    )
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                            .fillMaxWidth()
                            .padding(end = if (swipeOffsetX.value < -10f) 90.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset { IntOffset(swipeOffsetX.value.roundToInt(), 0) }
                                .pointerInput(Unit) {
                                    detectHorizontalDragGestures(
                                        onHorizontalDrag = { _, dragAmount ->
                                            val newOffset = (swipeOffsetX.value + dragAmount).coerceIn(-160f, 0f)
                                            coroutineScope.launch { swipeOffsetX.snapTo(newOffset) }
                                        },
                                        onDragEnd = {
                                            coroutineScope.launch {
                                                if (swipeOffsetX.value < -50f) {
                                                    swipeOffsetX.animateTo(-160f)
                                                } else {
                                                    swipeOffsetX.animateTo(0f)
                                                }
                                            }
                                        }
                                    )
                                }
                                .padding(vertical = 4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.security_icon),
                                contentDescription = "order",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(56.dp)
                            )
                            VSpacerWi(12f)
                            Column(modifier = Modifier.weight(1f)){
                                TitleStyle(
                                    text = "Қауіпсіздік",
                                    fontSize = 16f, fontWeight = 600
                                )
                                VSpacerHi(7f)
                                Text(
                                    text = "Күшті жел туралы ескерту. Абай болыңыз",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                            }
                            VSpacerWi(12f)
                            Column(){
                                Text(
                                    text = "15:25",
                                    style = TextStyle(
                                        fontSize = 11.sp,
                                        lineHeight = 11.sp,
                                        fontFamily = PrimaryFontFamily,
                                        fontWeight = FontWeight(400),
                                        color = AtasuaiTheme.colors.recommendTimeCo,
                                    )
                                )
                                VSpacerHi(10f)
                                Box(modifier = Modifier.size(24.dp)
                                    .background(color = Color(0xFF4F89FC), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "4",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.2.sp,
                                            fontFamily = PrimaryFontFamily  ,
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFFFFFFFF),
                                        )
                                    )
                                }
                            }
                        }
                    }

                }
            }

        }

    }
}