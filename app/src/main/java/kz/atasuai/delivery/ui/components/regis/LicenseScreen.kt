package kz.atasuai.delivery.ui.components.regis

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.ButtonStatus
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.components.global.GlobalButton
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.welcome.DocumentType
import kz.atasuai.delivery.ui.viewmodels.welcome.RegisDeliveryViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun LicenseScreen(
    viewModel: RegisDeliveryViewModel,
    context: Context,
    currentLanguage: LanguageModel,
    pageIndex: Int,
    onPre: () -> Unit,
    onNext: () -> Unit,
    currentPage: Int,
    totalPages: Int,
    pageOffset: Float
){
    val listState = rememberLazyListState()
    Column (modifier= Modifier.fillMaxSize()
        .background(AtasuaiTheme.colors.welcomeBac)
        .padding(horizontal = responsiveWidth(20f))
        .imePadding()
    ) {
        Spacer(modifier = Modifier.height(54.dp))
        Column(modifier = Modifier.fillMaxWidth().weight(0.9f)) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 40.dp)
            ){
                item{
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back_left_icon),
                            contentDescription = "back",
                            modifier = Modifier.size(16.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    onPre()
                                }
                        )
                        Text(
                            text = T("ls_Shutdown",currentLanguage),
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 20.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                            ),
                            modifier = Modifier
                                .noRippleClickable
                                {
                                    QarBaseViewModel.Navigator.navigateAndClearTask(context, ActivityList.MainActivity)
                                }
                        )

                    }
                    VSpacerHi(20f)
                    PageProgressIndicator(
                        currentPage = currentPage - 1,
                        totalPages = totalPages,
                        pageOffset = pageOffset
                    )
                }
                item{
                    VSpacerHi(40f)
                    Text(
                        text = "Жүргізуші куәлігі",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            fontFamily = PrimaryFontFamily,
                            fontWeight = FontWeight(500),
                            color = AtasuaiTheme.colors.documentTitleCo,
                        )
                    )
                    VSpacerHi(36f)
                    Row {
                        Text(
                            text = T("ls_Frontside", currentLanguage),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(300),
                                color = AtasuaiTheme.colors.documentTypeCo,
                            )

                        )

                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                    UploadTextField(viewModel,
                        documentType = DocumentType.LicenseFront,
                        currentLanguage = currentLanguage,
                        context = context
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(15.dp))
                    Row {
                        Text(
                            text = T("ls_Backside", currentLanguage),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                fontFamily = PrimaryFontFamily,
                                fontWeight = FontWeight(300),
                                color = AtasuaiTheme.colors.documentTypeCo,
                            )
                        )
                    }
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))

                    UploadTextField(viewModel,
                        documentType = DocumentType.LicenseBack,
                        currentLanguage = currentLanguage,
                        context = context
                    )
                }
            }
        }
        Column(modifier = Modifier.fillMaxWidth().weight(0.1f)) {
            GlobalButton(
                text = T("ls_Further", currentLanguage),
                status = ButtonStatus.Enabled ,
                onClick = {
                    onNext()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }

}