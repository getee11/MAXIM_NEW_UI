package com.example.maxim_project.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MapPlaceholder
import com.example.maxim_project.ui.components.ServiceCard
import com.example.maxim_project.ui.theme.*

@Composable
fun HomeTab(
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onWallet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = StatusBarHeight + SpaceXS, start = SpaceMD, end = SpaceMD, bottom = SpaceSM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(YellowLight)
                    .border(1.dp, MaximYellow, CircleShape)
            ) {
                Text("L", style = MaterialTheme.typography.titleMedium, color = MaximDarkGold)
            }
            Spacer(Modifier.width(SpaceSM))
            Column(modifier = Modifier.weight(1f)) {
                Text("Selamat Datang 👋", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                Text("Luthfi Kamil", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
            }
            // Wallet button
            IconButton(onClick = onWallet) {
                Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Wallet", tint = Sand, modifier = Modifier.size(22.dp))
            }
            // Notification bell
            IconButton(onClick = onNotifications) {
                BadgedBox(badge = {
                    Badge(containerColor = Error) { Text("3", style = MaterialTheme.typography.labelSmall, color = Canvas) }
                }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = TextPrimary, modifier = Modifier.size(22.dp))
                }
            }
        }

        // Search bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
                .clip(RoundedCornerShape(RadiusSM))
                .background(Surface)
                .clickable(onClick = onSearch)
                .padding(horizontal = SpaceMD, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Search, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(SpaceSM))
                Text("Mau ke mana?", style = MaterialTheme.typography.bodyMedium, color = TextMuted)
            }
        }

        Spacer(Modifier.height(SpaceMD))

        // Map placeholder
        MapPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = SpaceMD)
        )

        Spacer(Modifier.height(SpaceLG))

        // Service grid
        Text(
            "LAYANAN",
            style = MaterialTheme.typography.labelMedium,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = SpaceMD)
        )
        Spacer(Modifier.height(SpaceSM))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceXS),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ServiceCard(Icons.Default.TwoWheeler, "Motor", MaximYellow, YellowLight, onClick = onSearch)
            ServiceCard(Icons.Default.DirectionsCar, "Mobil", MaximGold, GoldLight, onClick = onSearch)
            ServiceCard(Icons.Default.LocalShipping, "Kurir", Blue, BlueLight, onClick = onSearch)
        }
        Spacer(Modifier.height(SpaceSM))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceXS),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ServiceCard(Icons.Default.Restaurant, "Makanan", Terracotta, TerracottaLight, onClick = onSearch)
            ServiceCard(Icons.Default.AirportShuttle, "Minivan", Purple, PurpleLight, onClick = onSearch)
            ServiceCard(Icons.Default.LocalMall, "Belanja", Green, GreenLight, onClick = onSearch)
        }

        Spacer(Modifier.height(SpaceLG))

        // Balance card
        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Canvas),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
                .clickable(onClick = onWallet)
        ) {
            Row(
                modifier = Modifier.padding(SpaceMD),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(RadiusSM))
                        .background(SandLight)
                ) {
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Sand, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(SpaceSM))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Saldo Maxim Pay", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    Text("Rp 150.000", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
            }
        }

        Spacer(Modifier.height(SpaceLG))

        // Promo banner carousel
        Text(
            "PROMO",
            style = MaterialTheme.typography.labelMedium,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = SpaceMD)
        )
        Spacer(Modifier.height(SpaceSM))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = SpaceMD),
            horizontalArrangement = Arrangement.spacedBy(SpaceSM)
        ) {
            PromoBanner("Diskon 30%", "Motor & Mobil", MaximYellow, MaximDarkGold)
            PromoBanner("Gratis Ongkir", "Kurir & Makanan", Blue, Canvas)
            PromoBanner("Cashback 15%", "Semua Layanan", Purple, Canvas)
        }

        Spacer(Modifier.height(SpaceLG + BottomBarHeight))
    }
}

@Composable
private fun PromoBanner(title: String, subtitle: String, bg: androidx.compose.ui.graphics.Color, textColor: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .width(240.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(RadiusMD))
            .background(bg)
            .padding(SpaceMD),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(title.uppercase(), style = MaterialTheme.typography.titleMedium, color = textColor)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = textColor.copy(alpha = 0.8f))
        }
    }
}
