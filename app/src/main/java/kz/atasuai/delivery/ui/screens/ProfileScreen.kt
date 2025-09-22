package kz.atasuai.delivery.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.components.profile.IncomeCard
import kz.atasuai.delivery.ui.components.profile.ProfileCard
import kz.atasuai.delivery.ui.components.profile.ProfileNav
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.profile.ProfileViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun ProfileScreen(
    context: Context,
    darkTheme: Boolean,
    viewModel: ProfileViewModel,
    currentLanguage: LanguageModel
){
    Column(modifier = Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.welcomeBac)
    ){
        VSpacerHi(54f)
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize().padding(horizontal = responsiveWidth(20f))
        ) {
            item{
                ProfileNav(currentLanguage,context,modifier = Modifier.fillMaxWidth())
            }
            item{
                VSpacerHi(28f)
                IncomeCard(currentLanguage,context, modifier = Modifier.fillMaxWidth())
            }
            item{
                VSpacerHi(16f)
                ProfileCard(currentLanguage,context,viewModel)
            }
        }

    }
}