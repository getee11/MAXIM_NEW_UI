package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Beranda", Icons.Outlined.Home),
    ORDERS("Pesanan", Icons.Outlined.Assignment),
    PROMOS("Promo", Icons.Outlined.LocalOffer),
    PROFILE("Profil", Icons.Outlined.Person)
}

@Composable
fun MaximBottomBar(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().background(Canvas)) {
        HorizontalDivider(color = Hairline.copy(alpha = 0.5f), thickness = 1.dp)
        NavigationBar(
            containerColor = Canvas,
            contentColor = TextPrimary,
            tonalElevation = 0.dp,
            windowInsets = NavigationBarDefaults.windowInsets
        ) {
            MainTab.entries.forEach { tab ->
                val selected = tab == currentTab
                NavigationBarItem(
                    selected = selected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        Icon(
                            tab.icon,
                            contentDescription = tab.label,
                            tint = if (selected) MaximDarkGold else TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            tab.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) MaximDarkGold else TextMuted
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = YellowLight,
                        selectedIconColor = MaximDarkGold,
                        unselectedIconColor = TextMuted,
                        selectedTextColor = MaximDarkGold,
                        unselectedTextColor = TextMuted
                    )
                )
            }
        }
    }
}
