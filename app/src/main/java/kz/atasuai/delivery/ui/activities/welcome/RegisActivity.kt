package kz.atasuai.delivery.ui.activities.welcome

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.components.global.noRippleClickable
import kz.atasuai.delivery.ui.components.regis.CarIDScreen
import kz.atasuai.delivery.ui.components.regis.ChooseModeScreen
import kz.atasuai.delivery.ui.components.regis.FaceIDScreen
import kz.atasuai.delivery.ui.components.regis.IDCardScreen
import kz.atasuai.delivery.ui.components.regis.LicenseScreen
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.delivery.ui.viewmodels.LanguageModalViewModel
import kz.atasuai.delivery.ui.viewmodels.welcome.LoginViewModel
import kz.atasuai.delivery.ui.viewmodels.welcome.RegisDeliveryViewModel
import kz.atasuai.market.models.LanguageModel
import kz.atasuai.market.ui.viewmodels.welcome.RegisterViewModel
import kz.atasuai.market.ui.viewmodels.welcome.VerificationViewModel

class RegisActivity:ComponentActivity(){
    private val themeManager by lazy { AtasuaiApp.themeManager }
    val viewModel: RegisDeliveryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by themeManager.themeMode.collectAsState()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }

            val context = LocalContext.current
            val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
            AtasuaiTheme(darkTheme = darkTheme) {
                AtasuaiScreen(
                    applySystemBarsPadding = false,
                    statusBarDarkIcons = true,
                    navigationBarDarkIcons = true,
                    navigationBarColor = AtasuaiTheme.colors.welcomeBac
                ) { screenModifier ->
                    RegisPagerView(viewModel,context,currentLanguage,modifier = screenModifier)
                }
            }
        }
    }
}


@Composable
fun RegisPagerView(
    viewModel: RegisDeliveryViewModel,
    context: Context,
    currentLanguage: LanguageModel,
    modifier: Modifier = Modifier
){
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val pageCount = 5
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pageCount }
    )
    val scope = rememberCoroutineScope()

    val animationSpec = remember {
        tween<Float>(
            durationMillis = 500,
            easing = CubicBezierEasing(0.25f, 0.1f, 0.25f, 1f)
        )
    }

    val navigateNext: () -> Unit = remember {
        {
            scope.launch {
                val next = (pagerState.currentPage + 1).coerceAtMost(pageCount - 1)
                pagerState.animateScrollToPage(page = next, animationSpec = animationSpec)
            }
        }
    }

    val navigatePrev: () -> Unit = remember {
        {
            scope.launch {
                val prev = (pagerState.currentPage - 1).coerceAtLeast(0)
                pagerState.animateScrollToPage(page = prev, animationSpec = animationSpec)
            }
        }
    }
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .noRippleClickable {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ,
        pageSpacing = 0.dp,
        beyondViewportPageCount = 1,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapAnimationSpec = animationSpec
        )
    ) { page ->
        val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = 1f - kotlin.math.abs(pageOffset * 0.3f).coerceAtMost(0.4f)
                    val scale = 1f - kotlin.math.abs(pageOffset * 0.05f)
                    scaleX = scale.coerceAtLeast(0.93f)
                    scaleY = scale.coerceAtLeast(0.93f)
                }
        ) {
            when (page) {
                0 -> ChooseModeScreen(
                    viewModel,
                    context,
                    currentLanguage,
                    pageIndex = page,
                    onPre = {  },
                    onNext = navigateNext,
                    currentPage = pagerState.currentPage,
                    totalPages = pageCount - 1,
                    pageOffset = pagerState.currentPageOffsetFraction
                )
                1 -> IDCardScreen(
                    viewModel,
                    context,
                    currentLanguage,
                    pageIndex = page,
                    onPre = navigatePrev,
                    onNext = navigateNext,
                    currentPage = pagerState.currentPage,
                    totalPages = pageCount - 1,
                    pageOffset = pagerState.currentPageOffsetFraction
                )
                2 -> LicenseScreen(
                    viewModel,
                    context,
                    currentLanguage,
                    pageIndex = page,
                    onPre = navigatePrev,
                    onNext = navigateNext,
                    currentPage = pagerState.currentPage,
                    totalPages = pageCount - 1,
                    pageOffset = pagerState.currentPageOffsetFraction
                )
                3 -> CarIDScreen(
                    viewModel,
                    context,
                    currentLanguage,
                    pageIndex = page,
                    onPre = navigatePrev,
                    onNext = navigateNext,
                    currentPage = pagerState.currentPage,
                    totalPages = pageCount - 1,
                    pageOffset = pagerState.currentPageOffsetFraction
                )
                4 -> FaceIDScreen(
                    viewModel,
                    context,
                    currentLanguage,
                    pageIndex = page,
                    onPre = navigatePrev,
                    onNext = navigateNext,
                    currentPage = pagerState.currentPage,
                    totalPages = pageCount - 1,
                    pageOffset = pagerState.currentPageOffsetFraction
                )

            }

        }
    }
}