package kz.atasuai.delivery.ui.components.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import kz.atasuai.delivery.common.ThemeManager
import kz.atasuai.delivery.common.ThemeMode
import kz.atasuai.delivery.ui.theme.AtasuaiScreen
import kz.atasuai.delivery.ui.theme.AtasuaiTheme
import kz.atasuai.market.ui.components.navigation.MainNavigation

@Composable
fun AtasuaiMainContent(themeManager: ThemeManager) {
    val themeMode by themeManager.themeMode.collectAsState()
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.FOLLOW_SYSTEM -> isSystemInDarkTheme()
    }

    AtasuaiTheme(darkTheme = darkTheme) {
        AtasuaiScreen(
            applySystemBarsPadding = false,
            statusBarDarkIcons = true,
            navigationBarDarkIcons = !darkTheme,
            navigationBarColor = Color.White
        ) { modifier ->
            MainNavigation(modifier = modifier, darkTheme = darkTheme)
        }
    }
}