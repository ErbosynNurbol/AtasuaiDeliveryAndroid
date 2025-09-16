package kz.atasuai.delivery.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.home.HomeNav
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun HomeScreen(
    context: Context,
    darkTheme: Boolean,
    viewModel: HomeScreenViewModel,
    currentLanguage: LanguageModel
){
    Column(modifier = Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.background)
    ){
        VSpacerHi(54f)
        HomeNav(modifier=Modifier.fillMaxWidth(),context,currentLanguage,viewModel)
    }
}