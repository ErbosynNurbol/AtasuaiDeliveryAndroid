package kz.atasuai.delivery.ui.components.regis

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.common.util.Hex
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.EmptyTitleStyle
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.welcome.DeliveryType
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
    val deliveryType by viewModel.deliveryType.collectAsState()
    Column (modifier= Modifier
        .fillMaxSize()
        .background(AtasuaiTheme.colors.welcomeBac)
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
        VSpacerHi(80f)
        Text(
            text = "Қандай курьер боласыз?",
            style = AtasuaiTheme.typography.EmptyTitleStyle
        )
        VSpacerHi(40f)
        Row(modifier = Modifier.fillMaxWidth()
            .noRippleClickable {
                viewModel.setDeliveryType(DeliveryType.Car)
                onNext()
            }
            .wrapContentHeight()
            .background(color = Color(0xFFF2F5F9), shape = RoundedCornerShape(size = 23.dp))
            .padding(16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.car_mode_icon),
                contentDescription = "car_mode_icon",
                modifier = Modifier.size(42.dp)
            )
            VSpacerWi(18f)
            Text(
                text = "Автокөлік",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.textSecondary,
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.right_shoulder),
                contentDescription = "to",
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )

        }
        VSpacerHi(5f)
        Row(modifier = Modifier.fillMaxWidth()
            .noRippleClickable {
                viewModel.setDeliveryType(DeliveryType.Motorcycle)
                onNext()
            }
            .wrapContentHeight()
            .background(color = Color(0xFFF2F5F9), shape = RoundedCornerShape(size = 23.dp))
            .padding(16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.moto_mode_icon),
                contentDescription = "car_mode_icon",
                modifier = Modifier.size(42.dp)
            )
            VSpacerWi(18f)
            Text(
                text = "Мотоцикль",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.textSecondary,
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.right_shoulder),
                contentDescription = "to",
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )

        }
        VSpacerHi(5f)
        Row(modifier = Modifier.fillMaxWidth()
            .noRippleClickable {
                viewModel.setDeliveryType(DeliveryType.Walking)
                onNext()
            }
            .wrapContentHeight()
            .background(color = Color(0xFFF2F5F9), shape = RoundedCornerShape(size = 23.dp))
            .padding(16.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.walking_mode_icon),
                contentDescription = "car_mode_icon",
                modifier = Modifier.size(42.dp)
            )
            VSpacerWi(18f)
            Text(
                text = "Жаяу жеткізу",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = PrimaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = AtasuaiTheme.colors.textSecondary,
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.right_shoulder),
                contentDescription = "to",
                tint = Color.Unspecified,
                modifier = Modifier.size(16.dp)
            )

        }



    }

}