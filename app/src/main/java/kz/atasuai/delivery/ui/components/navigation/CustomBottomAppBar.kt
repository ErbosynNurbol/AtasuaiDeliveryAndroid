package kz.atasuai.market.ui.components.navigation

import android.content.Context
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kz.atasuai.delivery.R
import kz.atasuai.delivery.common.Translator.T
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.theme.AtasuaiColors.PrimaryColor
import kz.atasuai.delivery.ui.theme.PrimaryFontFamily
import kz.atasuai.delivery.ui.theme.topShadow


@Composable
fun CustomBottomAppBar(
    navController: NavHostController,
    context: Context
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .topShadow(elevation = 15.dp)
                .background(Color.White)
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                iconRes = R.drawable.home_icon,
                iconResSelected = R.drawable.home_selected_icon,
                label = T("ls_Home", currentLanguage),
                selected = currentRoute == "home",
                onClick = { navController.navigateSingleTop("home") }
            )

            BottomNavItem(
                iconRes = R.drawable.order_icon,
                iconResSelected = R.drawable.order_selected_icon,
                label = T("ls_Order", currentLanguage),
                selected = currentRoute == "order",
                onClick = { navController.navigateSingleTop("order") }
            )

            // 空白占位给 QR 图标
            Spacer(modifier = Modifier.weight(1f))

            BottomNavItem(
                iconRes = R.drawable.notification_icon,
                iconResSelected = R.drawable.notification_selected_icon,
                label = T("ls_Message", currentLanguage),
                selected = currentRoute == "notification",
                onClick = { navController.navigateSingleTop("notification") }
            )

            BottomNavItem(
                iconRes = R.drawable.profile_icon,
                iconResSelected = R.drawable.profile_selected_icon,
                label = T("ls_Profile", currentLanguage),
                selected = currentRoute == "profile",
                onClick = { navController.navigateSingleTop("profile") }
            )
        }

        Icon(
            painter = painterResource(R.drawable.show_qr_icon),
            contentDescription = "QR Code",
            tint = Color.Unspecified,
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-27).dp)
                .shadow(
                    elevation = 5.dp,
                    shape = CircleShape,
                    ambientColor = Color(0xFF4F89FC),
                    spotColor = Color(0xFF4F89FC)
                )
                .clickable {
                    navController.navigateSingleTop("qrScreen")
                }
        )
    }
}

@Composable
fun RowScope.BottomNavItem(
    iconRes: Int,
    iconResSelected: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val color = if (selected) PrimaryColor else Color(0xFF595959)
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "BottomNavItemScale"
    )

    Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(scale)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            tryAwaitRelease()
                            isPressed = false
                            onClick()
                        }
                    )
                }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (selected) iconResSelected else iconRes
                ),
                contentDescription = label,
                tint =Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = color,
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.Center,
                    fontFamily = PrimaryFontFamily
                )
            )
        }
    }
}

fun NavHostController.navigateSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

