package com.example.maxim_project.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.maxim_project.navigation.MaximNavGraph
import com.example.maxim_project.navigation.Screen
import com.example.maxim_project.ui.components.MainTab
import com.example.maxim_project.ui.components.MaximBottomBar
import com.example.maxim_project.ui.components.OrderData

@Composable
fun MaximApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var selectedOrder by remember { mutableStateOf<OrderData?>(null) }

    // Map the route string to MainTab enum
    val currentTab = when (currentRoute) {
        Screen.Home.route -> MainTab.HOME
        Screen.Orders.route -> MainTab.ORDERS
        Screen.Promos.route -> MainTab.PROMOS
        Screen.Profile.route -> MainTab.PROFILE
        else -> null
    }

    Scaffold(
        bottomBar = {
            if (currentTab != null) {
                MaximBottomBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        val targetRoute = when (tab) {
                            MainTab.HOME -> Screen.Home.route
                            MainTab.ORDERS -> Screen.Orders.route
                            MainTab.PROMOS -> Screen.Promos.route
                            MainTab.PROFILE -> Screen.Profile.route
                        }
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MaximNavGraph(
            navController = navController,
            onOrderSelected = { selectedOrder = it },
            getSelectedOrder = { selectedOrder },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
