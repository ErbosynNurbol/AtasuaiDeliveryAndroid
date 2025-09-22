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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kz.atasuai.delivery.datastore.OnlineMode
import kz.atasuai.delivery.datastore.OnlineMode.setOnline
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.components.order.JolayEmptyCard
import kz.atasuai.delivery.ui.components.order.OrderNav
import kz.atasuai.delivery.ui.components.order.OrderTypeSwitch
import kz.atasuai.delivery.ui.components.order.PlannedEmptyCard
import kz.atasuai.delivery.ui.components.order.PrepareItem
import kz.atasuai.delivery.ui.components.order.SendingItem
import kz.atasuai.delivery.ui.components.order.ZholaiItem
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.order.OrderViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun OrderScreen(
    context: Context,
    darkTheme: Boolean,
    viewModel: OrderViewModel,
    currentLanguage: LanguageModel,
    navController: NavHostController,
){
    val isPlanned by viewModel.isPlanned.collectAsState()
    Column(modifier = Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.background)
        .padding(horizontal = responsiveWidth(20f))
    ){
        VSpacerHi(54f)
        OrderNav(currentLanguage,modifier = Modifier.fillMaxWidth())
        VSpacerHi(18f)
        OrderTypeSwitch(
            isOnline = isPlanned,
            onStatusChange = {
                viewModel.updateOrderType(it)
            },
            currentLanguage = currentLanguage,
            modifier = Modifier.fillMaxWidth(),
            darkTheme = darkTheme
        )
        if(isPlanned){
            VSpacerHi(17f)
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxWidth()
            ){
                item{
                    SendingItem()
                }
                item{
                    VSpacerHi(7f)
                    PrepareItem()
                }
            }
//            PlannedEmptyCard(currentLanguage, modifier = Modifier.fillMaxWidth(),navController)
        }else{
            VSpacerHi(17f)
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxWidth()
            ){
                item{
                    ZholaiItem()
                }
                item{
                    VSpacerHi(7f)
                    PrepareItem(alpha = 0.6f)
                }

//            JolayEmptyCard(currentLanguage, modifier = Modifier.fillMaxWidth())
           }
        }
    }
}