package kz.atasuai.delivery.ui.components.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.common.navigtion.ActivityList
import kz.atasuai.delivery.ui.components.global.VSpacerHi
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.theme.ProfileTitleStyle
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.profile.ProfileViewModel
import kz.atasuai.market.models.LanguageModel

@Composable
fun ProfileCard(
    currentLanguage:LanguageModel,
    context: Context,
    viewModel:ProfileViewModel,
){
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.account_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Account",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(2f)
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo)
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.wallet_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Wallet",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(2f)
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo,shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.history_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Orderhistory",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(14f)
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.document_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Documents",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(2f)
    Row(modifier = Modifier.fillMaxWidth()
        .noRippleClickable {
            QarBaseViewModel.Navigator.navigate(context, ActivityList.SupportActivity)
        }
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo,shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.support_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Supportandhelp",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(14f)
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.setting_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Settings",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
    VSpacerHi(2f)
    Row(modifier = Modifier.fillMaxWidth()
        .wrapContentHeight()
        .background(color = AtasuaiTheme.colors.profileCardCo,shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 20.dp, bottomEnd = 20.dp))
        .padding(horizontal = 20.dp, vertical = 13.5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = R.drawable.about_icon),
            contentDescription = "account",
            tint = Color.Unspecified,
            modifier = Modifier.height(29.dp)
        )
        VSpacerWi(12f)

        Text(
            text = T("ls_Abouttheplatform",currentLanguage),
            style = AtasuaiTheme.typography.ProfileTitleStyle,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.right_shoulder),
            contentDescription = "right_shoulder",
            tint = Color(0xFFD6D6D6),
            modifier = Modifier.size(14.dp)
        )

    }
}