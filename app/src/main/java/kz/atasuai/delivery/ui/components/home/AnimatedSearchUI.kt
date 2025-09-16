package kz.atasuai.delivery.ui.components.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.datastore.OnlineMode
import kz.atasuai.delivery.ui.components.global.VSpacerWi
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.global.responsiveWidth
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel
import kz.atasuai.market.models.LanguageModel


@Composable
fun AnimatedSearchUI(
    changeSearch: Boolean,
    keyWord: String,
    currentLanguage: LanguageModel,
    viewModel: HomeScreenViewModel,
) {
    val isOnline by OnlineMode.isOnline.collectAsState()
    val searchWidthProgress by animateFloatAsState(
        targetValue = if (changeSearch) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "searchWidth"
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (changeSearch) 0f else 1f,
        animationSpec = tween(
            durationMillis = 200,
            delayMillis = if (changeSearch) 0 else 100
        ),
        label = "buttonAlpha"
    )

    AnimatedContent(
        targetState = changeSearch,
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { width -> if (targetState) width else -width },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeIn(
                animationSpec = tween(200, delayMillis = 50)
            ) togetherWith slideOutHorizontally(
                targetOffsetX = { width -> if (targetState) -width else width },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            ) + fadeOut(
                animationSpec = tween(200)
            )
        },
        label = "searchContent"
    ) { isSearchMode ->
        if (!isSearchMode) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .alpha(buttonAlpha)
                        .noRippleClickable {
                            viewModel.setChangeSearch(true)
                        }
                        .width(50.dp)
                        .height(50.dp)
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "search",
                        modifier = Modifier.size(24.dp)
                    )
                }

                OnlineOfflineSwitch(
                    isOnline = isOnline,
                    onStatusChange = {
                        OnlineMode.setOnline(it)
                    },
                    currentLanguage = currentLanguage,
                    modifier = Modifier.width(204.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "filter",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchTextFiled(
                    text = keyWord,
                    onTextChange = {
                        viewModel.setKeyWord(it)
                    },
                    placeholderText = "ls_Search",
                    currentLanguage,
                    modifier = Modifier
                        .weight(1f)
                        .height(responsiveWidth(52f)),
                    onChange = {}
                )

                Text(
                    text = T("ls_Cancel", currentLanguage),
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 14.3.sp,
                        fontFamily = PrimaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF595959),
                    ),
                    modifier = Modifier
                        .noRippleClickable {
                            viewModel.setChangeSearch(false)
                        }
                )
            }
        }
    }
}