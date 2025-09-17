package kz.atasuai.delivery.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.EmptyDesStyle
import kz.atasuai.delivery.ui.theme.EmptyTitleStyle
import kz.atasuai.market.models.LanguageModel

@Composable
fun OfflineCard(currentLanguage: LanguageModel,modifier: Modifier){
   Column(modifier = modifier.fillMaxSize(),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
       ){
       Image(
           painter = painterResource(id = R.drawable.offline_card_icon),
           contentDescription = "Offline",
           modifier = Modifier.width(210.dp).height(140.dp)
       )
       VSpacerHi(35f)
       Text(
           text = "Сіз офлайн режимдесіз",
           style = AtasuaiTheme.typography.EmptyTitleStyle
       )
       VSpacerHi(16f)
       Text(
           text = "Тапсырыс ұсыныстарын алу үшін онлайн режиміне өтіңіз",
           style = AtasuaiTheme.typography.EmptyDesStyle,
           textAlign = TextAlign.Center,
           modifier = Modifier
               .width(238.dp)
       )
   }
}

@Composable
fun EmptyProposal(currentLanguage: LanguageModel,modifier: Modifier){
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.empty_proposal_icon),
            contentDescription = "Offline",
            modifier = Modifier.width(210.dp).height(140.dp)
        )
        VSpacerHi(35f)
        Text(
            text = "Әзірге ұсыныстар жоқ",
            style = AtasuaiTheme.typography.EmptyTitleStyle
        )
        VSpacerHi(16f)
        Text(
            text = "Жеткізу бойынша ұсыныстар кейін осында пайда болады",
            style = AtasuaiTheme.typography.EmptyDesStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .width(238.dp)
        )
    }
}
