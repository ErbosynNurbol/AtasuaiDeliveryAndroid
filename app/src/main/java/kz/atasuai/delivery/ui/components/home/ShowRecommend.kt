package kz.atasuai.delivery.ui.components.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.market.models.LanguageModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ShowRecommend(currentLanguage: LanguageModel,
                  onDismissRequest: () -> Unit,){
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.padding(0.dp),
                width = 65.dp,
                height = 5.dp,
                color = colorResource(id = R.color.switch_bac_co),
                shape = RoundedCornerShape(10.dp)
            )
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(screenHeight * 0.3f)
        ) {
            Column(modifier = Modifier.weight(0.75f)) {
            }
            Row(modifier = Modifier.fillMaxWidth().weight(0.25f),
                verticalAlignment = Alignment.CenterVertically
            ){
                GlobalButton(
                    text = T("ls_Accept",currentLanguage),
                    containerColor = Color(0xFFFE6D78),
                    loadingText = T("ls_Loading",currentLanguage),
                    status = ButtonStatus.Enabled,
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
    }
}