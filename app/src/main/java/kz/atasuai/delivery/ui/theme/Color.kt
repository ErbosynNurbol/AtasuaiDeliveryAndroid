package kz.atasuai.delivery.ui.theme

import androidx.compose.ui.graphics.Color

object AtasuaiColors {
    // 主要颜色
    val Seashell = Color(0xFFF7F3F0)
    val Laurel = Color(0xFF769C6B)
    val Silver = Color(0xFFC0C0C0)
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)
    val Black20 = Color(0x33000000)
    val PrimaryColor = Color(0xFF4F89FC)

    // Light主题颜色
    object Light {
        val bacLightColor = Color(0xFFF5F6FB).copy(alpha = 0.5f)
        val welcomeBac = Color.White
        val textPrimary = Black
        val textSecondary = Color(0xFF666666)
        val textTertiary = Color(0xFF393939)
        val textDisabled = Color(0xFFCCCCCC)
        val placeholderCo = Color(0xFFA6A8B5)
        val cardBorderCo = Color(0xFFE8EBF1)
        val textLink = Laurel
        val buttonSecondary = Silver
        val border = Color(0xFFE0E0E0)
        val divider = Color(0xFFF4F4F4)
        val background = Seashell
        val surface = White
    }

    // Dark主题颜色
    object Dark {
        val bacDarkColor = Color(0xFF202020)
        val welcomeBac = Color(0xFF202020)
        val textPrimary = White
        val textSecondary = Color(0xFFB3B3B3)
        val textTertiary = Color(0xFF666666)
        val textDisabled = Color(0xFF333333)
        val placeholderCo = Color(0xFFA3A3A3)
        val cardBorderCo = Color(0xFFE8EAEE)
        val textLink = Color(0xFF8FB485)
        val buttonSecondary = Color(0xFF4A4A4A)
        val border = Color(0xFF333333)
        val divider = Color(0xFF2A2A2A)
        val background = Color(0xFF121212)
        val surface = Color(0xFF1E1E1E)
    }
}