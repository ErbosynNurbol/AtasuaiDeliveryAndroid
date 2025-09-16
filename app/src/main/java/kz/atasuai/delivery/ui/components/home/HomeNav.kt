package kz.atasuai.delivery.ui.components.home

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun HomeNav(modifier: Modifier, context: Context, currentLanguage: LanguageModel,
            viewModel: HomeScreenViewModel,
            ){
    val changeSearch by viewModel.changeSearch.collectAsState()
    val keyWord by viewModel.keyWord.collectAsState()
    Row(modifier=Modifier.fillMaxWidth()
        .padding(horizontal = responsiveWidth(20f)),
    ){
        AnimatedSearchUI(
            changeSearch = changeSearch,
            keyWord = keyWord,
            currentLanguage = currentLanguage,
            viewModel = viewModel,
        )
    }
}