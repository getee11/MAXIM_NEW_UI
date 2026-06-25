package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

enum class MainTab(val label: String, val icon: ImageVector) {
    HOME("Beranda", Icons.Default.Home),
    ORDERS("Pesanan", Icons.Default.Receipt),
    PROMOS("Promo", Icons.Default.LocalOffer),
    PROFILE("Akun", Icons.Default.Person)
}

@Composable
fun MaximBottomBar(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(color = Hairline, thickness = 1.dp)
        NavigationBar(
            containerColor = Canvas,
            contentColor = TextPrimary,
            tonalElevation = 0.dp,
            modifier = Modifier.height(BottomBarHeight)
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
                            modifier = Modifier.size(22.dp)
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
                        indicatorColor = YellowLight
                    )
                )
            }
        }
    }
}
