package kz.atasuai.delivery.ui.components.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily


@Composable
fun NoInternetPage(){
    var isLoading by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize().background(AtasuaiTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "no internet",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Байланыс жоқ",
                style = TextStyle(
                    fontSize = 23.sp,
                    lineHeight = 36.34.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(700),
                    color = AtasuaiTheme.colors.textPrimary,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Интернет байланысын тексеріп көріңіз",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.12.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF757575),
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .clickable (
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        isLoading = true
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            isLoading = false
                        }
                    }
                    .border(width = 1.dp, color = Color(0xFF4F89FC), shape = RoundedCornerShape(size = 10.dp))
                .width(193.dp)
                .height(41.dp)
                .padding(start = 31.dp, top = 12.dp, end = 31.dp, bottom = 12.dp)
                ,
                contentAlignment = Alignment.Center
            ){
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = PrimaryColor,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Қайта байқап көру",
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
}