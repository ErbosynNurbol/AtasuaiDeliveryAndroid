package kz.atasuai.delivery.ui.components.global

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.CircularBarsLoading
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.viewmodels.LanguageModalViewModel
import kz.atasuai.market.models.LanguageModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageModal(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onLanguageSelected: (LanguageModel) -> Unit,
    viewModel: LanguageModalViewModel = viewModel()
) {
    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
    val languageList by viewModel.languageList.observeAsState(emptyList())

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
                .background(Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(horizontal = 20.dp)
                .height(screenHeight * 0.4f)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            if (languageList.isEmpty()) {
                CircularBarsLoading(
                    modifier = Modifier.fillMaxSize(),
                    background = Color.White
                )
            } else {
                languageList.forEachIndexed { index, language ->
                    LanguageOption(
                        language = language,
                        isSelected = language.culture == currentLanguage.culture,
                        onLanguageSelected = onLanguageSelected,
                        showDivider = index < languageList.size - 1
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

    }
}

@Composable
fun LanguageOption(
    language: LanguageModel,
    isSelected: Boolean,
    onLanguageSelected: (LanguageModel) -> Unit,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLanguageSelected(language) }
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
            ) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(language.flagUrl)
                        .decoderFactory(SvgDecoder.Factory())
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "${language.fullName} flag",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = language.fullName,
                fontSize = 15.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_language),
                    contentDescription = "Selected",
                    tint = PrimaryColor
                )
            }
        }
        if (showDivider) {
            Spacer(
                modifier = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(color = Color(0xFFEDEDED))
            )
        }
    }
}
