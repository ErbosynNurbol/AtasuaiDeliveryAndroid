package kz.atasuai.delivery.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.components.home.EmptyProposal
import kz.atasuai.delivery.ui.components.home.HomeNav
import kz.atasuai.delivery.ui.components.home.HorizontalSlide
import kz.atasuai.delivery.ui.components.home.OfflineCard
import kz.atasuai.delivery.ui.components.home.RecommendCard
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.ProposalNameStyle
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun HomeScreen(
    context: Context,
    darkTheme: Boolean,
    viewModel: HomeScreenViewModel,
    currentLanguage: LanguageModel
){
    val showRecommend by remember{ multbleStateFlow(false)}
    Column(modifier = Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.background)
    ){
        VSpacerHi(54f)
        HomeNav(modifier=Modifier.fillMaxWidth(),context,currentLanguage,viewModel)
        VSpacerHi(20f)
        HorizontalSlide(viewModel)
        VSpacerHi(20f)
        Row(modifier = Modifier.fillMaxWidth()
            .padding(horizontal = responsiveWidth(20f))
        ){
            Text(
                text = "Бүгінге ұсыныстар",
                style = AtasuaiTheme.typography.ProposalNameStyle
            )
        }
        VSpacerHi(16f)
        LazyColumn (
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(horizontal = responsiveWidth(20f))
            ,
            state = rememberLazyListState(),
        ){
            item{
                RecommendCard(modifier = Modifier.fillMaxWidth(),onClick = {})
                VSpacerHi(6f)
            }
            item{
                RecommendCard(modifier = Modifier.fillMaxWidth(),onClick = {})
            }
        }

//        EmptyProposal(currentLanguage,modifier=Modifier.weight(1f))

    }
}