package kz.atasuai.delivery.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kz.atasuai.delivery.R


val PrimaryFontFamily = FontFamily(
    Font(R.font.inter_font, FontWeight.Normal),
    Font(R.font.suisse_intl_semi_bold, FontWeight.Bold),
    Font(R.font.suisse_intl_medium, FontWeight.Medium)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Typography.CargoPlaceStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 18.sp,
        lineHeight = 18.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = AtasuaiTheme.colors.placeholderCo,
    )

val Typography.ProposalIdStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 14.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = AtasuaiTheme.colors.textPrimary,
    )

val Typography.ProposalTimeStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 12.sp,
        lineHeight = 12.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(400),
        color = AtasuaiTheme.colors.textTertiary,
    )

val Typography.ProposalNameStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 18.sp,
        lineHeight = 18.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = AtasuaiTheme.colors.textSecondary,
    )

val Typography.EmptyTitleStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 18.sp,
        lineHeight = 21.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(600),
        color = AtasuaiTheme.colors.emptyTitleCo,
    )

val Typography.EmptyDesStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(300),
        color = AtasuaiTheme.colors.emptyDesCo,
    )

val Typography.ProfileTitleStyle: TextStyle
    @Composable
    get() = TextStyle(
        fontSize = 15.sp,
        lineHeight = 15.sp,
        fontFamily = PrimaryFontFamily,
        fontWeight = FontWeight(400),
        color = Color(0xFF121212),
    )