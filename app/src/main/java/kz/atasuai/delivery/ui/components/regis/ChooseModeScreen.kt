package kz.atasuai.delivery.ui.components.regis

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.viewmodels.welcome.RegisDeliveryViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun ChooseModeScreen(viewModel: RegisDeliveryViewModel,
                     context: Context,
                     currentLanguage:LanguageModel,
                     pageIndex: Int,
                     onPre: () -> Unit,
                     onNext: () -> Unit,
                     currentPage: Int,
                     totalPages: Int,
                     pageOffset: Float
){
    Column (modifier= Modifier
        .fillMaxSize()
        .padding(horizontal = responsiveWidth(20f))
        .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.app_dark_logo),
            contentDescription = "shop_share_icon",
            modifier = Modifier
                .padding(0.dp)
                .width(147.dp)
                .height(31.dp)
        )

    }

}