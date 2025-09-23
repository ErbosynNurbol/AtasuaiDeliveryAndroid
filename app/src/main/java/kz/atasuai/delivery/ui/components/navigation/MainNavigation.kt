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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kz.atasuai.delivery.ui.components.global.NoInternetPage
import kz.atasuai.delivery.ui.screens.HomeScreen
import kz.atasuai.delivery.ui.screens.NotificationScreen
import kz.atasuai.delivery.ui.screens.OrderScreen
import kz.atasuai.delivery.ui.screens.ProfileScreen
import kz.atasuai.delivery.ui.screens.ScanQRModal
import kz.atasuai.delivery.ui.theme.navigationBarsPadding
import kz.atasuai.delivery.ui.viewmodels.QarBaseViewModel
import kz.atasuai.delivery.ui.viewmodels.home.HomeScreenViewModel
import kz.atasuai.delivery.ui.viewmodels.home.ScanQRViewModel
import kz.atasuai.delivery.ui.viewmodels.notification.NotificationViewModel
import kz.atasuai.delivery.ui.viewmodels.order.OrderViewModel
import kz.atasuai.delivery.ui.viewmodels.profile.ProfileViewModel


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
    val reportViewModel: NotificationViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val qrViewModel: ScanQRViewModel = viewModel()

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
                    context = context,qrViewModel
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
                            HomeScreen(context, darkTheme, viewModel, currentLanguage)
                        }
                        composable("order") {
                            OrderScreen(context, darkTheme, orderViewModel, currentLanguage,navController)
                        }
                        composable("notification") {
                            NotificationScreen(context, darkTheme, reportViewModel, currentLanguage)
                        }

                        composable("profile") {
                            ProfileScreen(context, darkTheme, profileViewModel, currentLanguage)
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