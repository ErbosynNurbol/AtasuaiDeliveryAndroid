package kz.atasuai.delivery.ui.theme


import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.ui.AtasuaiApp


private val LightColorScheme = lightColorScheme(
    primary = AtasuaiColors.Laurel,
    onPrimary = AtasuaiColors.White,
    secondary = AtasuaiColors.Silver,
    onSecondary = AtasuaiColors.Black,
    background = AtasuaiColors.Light.background,
    onBackground = AtasuaiColors.Light.textPrimary,
    surface = AtasuaiColors.Light.surface,
    onSurface = AtasuaiColors.Light.textPrimary,
    surfaceVariant = AtasuaiColors.Seashell,
    onSurfaceVariant = AtasuaiColors.Light.textSecondary
)

private val DarkColorScheme = darkColorScheme(
    primary = AtasuaiColors.Laurel,
    onPrimary = AtasuaiColors.White,
    secondary = AtasuaiColors.Silver,
    onSecondary = AtasuaiColors.White,
    background = AtasuaiColors.Dark.background,
    onBackground = AtasuaiColors.Dark.textPrimary,
    surface = AtasuaiColors.Dark.surface,
    onSurface = AtasuaiColors.Dark.textPrimary,
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = AtasuaiColors.Dark.textSecondary
)

// 自定义颜色系统
@Immutable
data class AtasuaiCustomColors(
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textDisabled: Color,
    val textLink: Color,
    val buttonSecondary: Color,
    val border: Color,
    val divider: Color,
    val backgroundCustom: Color,
    val surfaceCustom: Color,
    val placeholderCo: Color,
    val cardBorderCo: Color,
    val welcomeBac:Color,
    val emptyTitleCo :Color,
    val emptyDesCo :Color,
    val recommendTimeCo:Color,
    val documentTitleCo:Color,
    val documentTypeCo:Color,
    val profileCardCo:Color,
)

val LocalAtasuaiColors = staticCompositionLocalOf {
    AtasuaiCustomColors(
        background = Color.Unspecified,
        textPrimary = Color.Unspecified,
        textSecondary = Color.Unspecified,
        textTertiary = Color.Unspecified,
        textDisabled = Color.Unspecified,
        textLink = Color.Unspecified,
        buttonSecondary = Color.Unspecified,
        border = Color.Unspecified,
        divider = Color.Unspecified,
        backgroundCustom = Color.Unspecified,
        surfaceCustom = Color.Unspecified,
        placeholderCo= Color.Unspecified,
        cardBorderCo= Color.Unspecified,
        welcomeBac = Color.Unspecified,
        emptyTitleCo = Color.Unspecified,
        emptyDesCo = Color.Unspecified,
        recommendTimeCo=Color.Unspecified,
        documentTitleCo = Color.Unspecified,
        documentTypeCo = Color.Unspecified,
        profileCardCo = Color.Unspecified
    )
}

private val lightCustomColors = AtasuaiCustomColors(
    background = AtasuaiColors.Light.bacLightColor,
    textPrimary = AtasuaiColors.Light.textPrimary,
    textSecondary = AtasuaiColors.Light.textSecondary,
    textTertiary = AtasuaiColors.Light.textTertiary,
    textDisabled = AtasuaiColors.Light.textDisabled,
    textLink = AtasuaiColors.Light.textLink,
    buttonSecondary = AtasuaiColors.Light.buttonSecondary,
    border = AtasuaiColors.Light.border,
    divider = AtasuaiColors.Light.divider,
    backgroundCustom = AtasuaiColors.Light.background,
    surfaceCustom = AtasuaiColors.Light.surface,
    placeholderCo= AtasuaiColors.Light.placeholderCo,
    cardBorderCo= AtasuaiColors.Light.cardBorderCo,
    welcomeBac = AtasuaiColors.Light.welcomeBac,
    emptyTitleCo = AtasuaiColors.Light.emptyTitleCo,
    emptyDesCo = AtasuaiColors.Light.emptyDesCo,
    recommendTimeCo = AtasuaiColors.Light.recommendTimeCo,
    documentTitleCo = AtasuaiColors.Light.documentTitleCo,
    documentTypeCo = AtasuaiColors.Light.documentTypeCo,
    profileCardCo = AtasuaiColors.Light.profileCardCo
)

private val darkCustomColors = AtasuaiCustomColors(
    background = AtasuaiColors.Dark.bacDarkColor,
    textPrimary = AtasuaiColors.Dark.textPrimary,
    textSecondary = AtasuaiColors.Dark.textSecondary,
    textTertiary = AtasuaiColors.Dark.textTertiary,
    textDisabled = AtasuaiColors.Dark.textDisabled,
    textLink = AtasuaiColors.Dark.textLink,
    buttonSecondary = AtasuaiColors.Dark.buttonSecondary,
    border = AtasuaiColors.Dark.border,
    divider = AtasuaiColors.Dark.divider,
    backgroundCustom = AtasuaiColors.Dark.background,
    surfaceCustom = AtasuaiColors.Dark.surface,
    placeholderCo = AtasuaiColors.Dark.placeholderCo,
    cardBorderCo = AtasuaiColors.Dark.cardBorderCo,
    welcomeBac = AtasuaiColors.Dark.welcomeBac,
    emptyTitleCo = AtasuaiColors.Dark.emptyTitleCo,
    emptyDesCo = AtasuaiColors.Dark.emptyDesCo,
    recommendTimeCo = AtasuaiColors.Dark.recommendTimeCo,
    documentTitleCo = AtasuaiColors.Dark.documentTitleCo,
    documentTypeCo = AtasuaiColors.Dark.documentTypeCo,
    profileCardCo = AtasuaiColors.Dark.profileCardCo
)

// 主题Composable
@Composable
fun AtasuaiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val customColors = if (darkTheme) darkCustomColors else lightCustomColors

    CompositionLocalProvider(
        LocalAtasuaiColors provides customColors,
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

object AtasuaiTheme {
    val colors: AtasuaiCustomColors
        @Composable
        get() = LocalAtasuaiColors.current

    val materialColors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}

// 系统栏Padding扩展
@Composable
fun Modifier.systemBarsPadding(): Modifier {
    val insets = WindowInsets.systemBars
    return this.windowInsetsPadding(insets)
}

@Composable
fun Modifier.statusBarsPadding(): Modifier {
    val insets = WindowInsets.statusBars
    return this.windowInsetsPadding(insets)
}

@Composable
fun Modifier.navigationBarsPadding(): Modifier {
    val insets = WindowInsets.navigationBars
    return this.windowInsetsPadding(insets)
}

@Composable
fun Modifier.topShadow(
    elevation: Dp,
    color: Color = Color(0x1A0B3053)
) = this.drawBehind {
    val shadowHeight = elevation.toPx()

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.Transparent,
                color.copy(alpha = 0.002f),
                color.copy(alpha = 0.005f),
                color.copy(alpha = 0.01f),
                color.copy(alpha = 0.02f),
                color.copy(alpha = 0.03f),
                color.copy(alpha = 0.04f),
            ),
            startY = -shadowHeight,
            endY = 0f
        ),
        topLeft = Offset(0f, -shadowHeight),
        size = Size(size.width, shadowHeight)
    )
}


@Composable
fun AtasuaiScreen(
    modifier: Modifier = Modifier,
    applySystemBarsPadding: Boolean = true,
    applyStatusBarPadding: Boolean = false,
    applyNavigationBarPadding: Boolean = false,
    statusBarDarkIcons: Boolean? = null,
    navigationBarDarkIcons: Boolean = true,
    navigationBarColor: Color = Color.White,
    content: @Composable (Modifier) -> Unit
) {
    val themeMode by AtasuaiApp.themeManager.themeMode.collectAsState(
        initial = ThemeMode.FOLLOW_SYSTEM
    )
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }

    // 其余代码不变
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = statusBarDarkIcons ?: !darkTheme,
        )
        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = navigationBarDarkIcons
        )
    }

    val screenModifier = modifier
        .fillMaxSize()
        .background(AtasuaiTheme.colors.backgroundCustom)
        .let { baseModifier ->
            when {
                applySystemBarsPadding -> baseModifier.systemBarsPadding()
                applyStatusBarPadding && applyNavigationBarPadding ->
                    baseModifier.statusBarsPadding().navigationBarsPadding()
                applyStatusBarPadding -> baseModifier.statusBarsPadding()
                applyNavigationBarPadding -> baseModifier.navigationBarsPadding()
                else -> baseModifier
            }
        }

    content(screenModifier)
}