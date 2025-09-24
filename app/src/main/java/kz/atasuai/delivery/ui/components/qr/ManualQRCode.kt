package kz.atasuai.delivery.ui.components.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.TitleStyle
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.viewmodels.home.ScanQRViewModel
import kz.atasuai.market.models.LanguageModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualQRCode(viewModel: ScanQRViewModel,onDismissRequest: () -> Unit,currentLanguage: LanguageModel){
    val cargoCode by viewModel.cargoCode.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    LaunchedEffect(Unit) {
        delay(300)
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                modifier = Modifier.padding(0.dp),
                width = 65.dp,
                height = 5.dp,
                color = colorResource(id = R.color.switch_bac_co),
                shape = RoundedCornerShape(10.dp)
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(horizontal = 20.dp)
                .height(screenHeight * 0.4f)
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
                ){
                TitleStyle(
                    text = "Кодты қолмен енгізу", fontSize = 16f, fontWeight = 600,
                )
            }
            Column(modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                ){
                ManualTextFiled(
                    modifier = Modifier.fillMaxWidth().height(54.dp).focusRequester(focusRequester),
                    text = cargoCode,
                    onTextChange = { viewModel.updateCode(it) },
                    placeholderText = "0000000",
                    currentLanguage = currentLanguage,
                    isError = false,
                    onChange = {},
                )
            }

            GlobalButton(
                text = T("ls_Search", currentLanguage),
                status = viewModel.codeBtnStatus ,
                onClick = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            VSpacerHi(16f)

        }
    }
}