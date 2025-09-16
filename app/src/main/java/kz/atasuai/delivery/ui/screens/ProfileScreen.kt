package kz.atasuai.delivery.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        .background(AtasuaiTheme.colors.background)
    ){

    }
}