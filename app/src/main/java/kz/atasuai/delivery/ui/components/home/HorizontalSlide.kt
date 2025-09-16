package kz.atasuai.delivery.ui.components.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kz.atasuai.delivery.R
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel


@Composable
fun HorizontalSlide(viewModel: HomeScreenViewModel) {
//    val bannerList by viewModel.bannerList.collectAsState()
    val context = LocalContext.current

//    if (bannerList.bannerList.isEmpty()) return
    val pageCount = Int.MAX_VALUE
    val initialPage = pageCount / 2
    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val targetWidth = screenWidth * 0.8f
    LaunchedEffect(pagerState) {
        delay(40000)
        while(true) {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
            delay(40000)
        }
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(start = 20.dp, end = 60.dp), // 右边留更多空间
        pageSpacing = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f/5f)
    ) { page ->
        val actualIndex = page % 3

        Box(
            modifier = Modifier
                .width(targetWidth) // 控制每页宽度，让下一页更明显露出
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    // 点击处理
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.horizontal_slide),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}