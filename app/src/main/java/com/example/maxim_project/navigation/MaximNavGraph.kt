package com.example.maxim_project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.maxim_project.ui.components.OrderData
import com.example.maxim_project.ui.screens.*
import com.example.maxim_project.ui.screens.home.*

@Composable
fun MaximNavGraph(
    navController: NavHostController,
    onOrderSelected: (OrderData) -> Unit,
    getSelectedOrder: () -> OrderData?,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Onboarding.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onDone = {
                navController.navigate(Screen.Auth.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }
        composable(Screen.Auth.route) {
            AuthScreen(onNext = {
                navController.navigate(Screen.OTP.route)
            })
        }
        composable(Screen.OTP.route) {
            OTPScreen(
                onBack = { navController.popBackStack() },
                onVerified = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeTab(
                onSearch = { navController.navigate(Screen.Location.route) },
                onNotifications = { navController.navigate(Screen.Notifications.route) },
                onWallet = { navController.navigate(Screen.Wallet.route) }
            )
        }
        composable(Screen.Orders.route) {
            OrdersTab(onOrderClick = { order ->
                onOrderSelected(order)
                navController.navigate(Screen.OrderDetail.route)
            })
        }
        composable(Screen.Promos.route) {
            PromosTab()
        }
        composable(Screen.Profile.route) {
            ProfileTab(
                onFAQ = { navController.navigate(Screen.FAQ.route) },
                onCS = { navController.navigate(Screen.CS.route) },
                onWallet = { navController.navigate(Screen.Wallet.route) },
                onLogout = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Location.route) {
            LocationScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Screen.Vehicle.route) }
            )
        }
        composable(Screen.Vehicle.route) {
            VehicleScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Screen.Confirm.route) }
            )
        }
        composable(Screen.Confirm.route) {
            ConfirmScreen(
                onBack = { navController.popBackStack() },
                onConfirm = { navController.navigate(Screen.Searching.route) }
            )
        }
        composable(Screen.Searching.route) {
            SearchingScreen(
                onCancel = { navController.popBackStack() },
                onFound = {
                    navController.navigate(Screen.Tracking.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(Screen.Tracking.route) {
            TrackingScreen(
                onBack = { navController.popBackStack() },
                onChat = { navController.navigate(Screen.DriverChat.route) },
                onCS = { navController.navigate(Screen.CS.route) },
                onReport = { navController.navigate(Screen.Report.route) },
                onDone = {
                    navController.navigate(Screen.Summary.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(Screen.DriverChat.route) {
            DriverChatScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Summary.route) {
            SummaryScreen(
                onClose = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onRate = {
                    navController.navigate(Screen.Rating.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
                    }
                }
            )
        }
        composable(Screen.Rating.route) {
            RatingScreen(
                onBack = { navController.popBackStack() },
                onDone = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onReport = { navController.navigate(Screen.Report.route) },
                onCS = { navController.navigate(Screen.CS.route) }
            )
        }
        composable(Screen.Report.route) {
            ReportScreen(
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack() },
                onCS = { navController.navigate(Screen.CS.route) }
            )
        }
        composable(Screen.Notifications.route) {
            NotificationsScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Wallet.route) {
            WalletScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.CS.route) {
            CSScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.OrderDetail.route) {
            val order = getSelectedOrder()
            if (order != null) {
                OrderDetailScreen(
                    order = order,
                    onBack = { navController.popBackStack() },
                    onReorder = {
                        navController.navigate(Screen.Location.route)
                    },
                    onReport = {
                        navController.navigate(Screen.Report.route)
                    },
                    onDownloadReceipt = { /* Simulating receipt download */ }
                )
            } else {
                navController.popBackStack()
            }
        }
        composable(Screen.FAQ.route) {
            FAQScreen(
                onBack = { navController.popBackStack() },
                onCS = { navController.navigate(Screen.CS.route) }
            )
        }
    }
}
