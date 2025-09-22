package kz.atasuai.delivery.ui.components.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.EmptyDesStyle
import kz.atasuai.delivery.ui.theme.EmptyTitleStyle
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.ui.components.navigation.navigateSingleTop

@Composable
fun JolayEmptyCard(currentLanguage: LanguageModel, modifier: Modifier){
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.empty_opportunistic),
            contentDescription = "Offline",
            modifier = Modifier.width(220.dp).height(147.dp)
        )
        VSpacerHi(35f)
        Text(
            text = "Әзірге жолай тапсырыстар жоқ",
            style = AtasuaiTheme.typography.EmptyTitleStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(167.dp)
        )
        VSpacerHi(16f)
        Text(
            text = "Сіздің маршрут бойынша жаңа тапсырыстар осында пайда болады",
            style = AtasuaiTheme.typography.EmptyDesStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(250.dp)
        )
    }
}

@Composable
fun PlannedEmptyCard(currentLanguage: LanguageModel, modifier: Modifier, navController: NavHostController,){
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.empty_planned),
            contentDescription = "Offline",
            modifier = Modifier.width(237.dp).height(158.dp)
        )
        VSpacerHi(35f)
        Text(
            text = "Сізде қабылданған тапсырыс жоқ",
            style = AtasuaiTheme.typography.EmptyTitleStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(180.dp)
        )
        VSpacerHi(16f)
        Text(
            text = "Басты бетке өту арқылы тапсырыс ұсыныстарын қабылдай аласыз",
            style = AtasuaiTheme.typography.EmptyDesStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(250.dp)
        )
        VSpacerHi(33f)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .noRippleClickable {
                        navController.navigateSingleTop("home")
                    }
                    .border(width = 1.dp, color = Color(0xFF4F89FC), shape = RoundedCornerShape(size = 10.dp))
                    .width(193.dp)
                    .height(41.dp)
                    .padding(start = 31.dp, top = 12.dp, end = 31.dp, bottom = 12.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Басты бетке өту",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = PrimaryColor,
                    )
                )
            }
        }
    }
}