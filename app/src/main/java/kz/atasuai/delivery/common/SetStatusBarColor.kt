package kz.atasuai.delivery.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetupStatusBarColor(
    color: Color = Color.Transparent,
    darkIcons: Boolean = true
) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = darkIcons
        )
        systemUiController.setNavigationBarColor(
            color = color,
            darkIcons = darkIcons
        )
    }
}