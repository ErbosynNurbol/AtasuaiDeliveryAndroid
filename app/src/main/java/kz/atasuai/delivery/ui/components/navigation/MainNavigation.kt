package kz.atasuai.market.ui.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.atasuai.delivery.common.ConnectivityObserver
import kz.atasuai.delivery.common.ConnectivityStatus
import kz.atasuai.delivery.ui.AtasuaiApp
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel


@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    qarBaseViewModel: QarBaseViewModel = viewModel()
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityObserver(context) }
    val status by connectivityObserver.status.collectAsState(initial = ConnectivityStatus.Available)
    val currentLanguage by AtasuaiApp.currentLanguage.collectAsState()
    val viewModel: HomeScreenViewModel = viewModel()
    val shopViewModel: ShopViewModel = viewModel()
    val bonusViewModel: BonusViewModel = viewModel()
    val profileViewModel: MenuViewModel = viewModel()

    val startRoute = qarBaseViewModel.currentRoute.value.takeIf { it.isNotEmpty() } ?: "home"

    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            backStackEntry.destination.route?.let { route ->
                qarBaseViewModel.updateCurrentRoute(route)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                CustomBottomAppBar(
                    navController = navController,
                    context = context
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startRoute,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = innerPadding.calculateBottomPadding()
                    )
                    .padding(top = 0.dp)
            ) {
                when (status) {
                    ConnectivityStatus.Available -> {
                        composable("home") {
                            val notificationViewModel: NotificationViewModel = viewModel()
                            HomeScreen(context, darkTheme, viewModel, notificationViewModel, currentLanguage)
                        }
                        composable("shop") {
                            ShopScreen(context, darkTheme, shopViewModel, currentLanguage)
                        }
                        composable("bonus") {
                            BonusScreen(context, darkTheme, bonusViewModel, currentLanguage)
                        }
                        composable("menu") {
                            MenuScreen(context, darkTheme, profileViewModel, currentLanguage)
                        }
                    }
                    ConnectivityStatus.Unavailable -> {
                        composable("home") { NoInternetPage() }
                        composable("shop") { NoInternetPage() }
                        composable("bonus") { NoInternetPage() }
                        composable("menu") { NoInternetPage() }
                    }
                }
            }
        }
    }
}