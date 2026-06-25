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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.components.MapPlaceholder
import com.example.maxim_project.ui.components.ServiceCard
import com.example.maxim_project.ui.theme.*
import com.example.maxim_project.data.InMemoryDatabase

@Composable
fun HomeTab(
    onSearch: () -> Unit,
    onNotifications: () -> Unit,
    onWallet: () -> Unit,
    onSeeAllPromos: () -> Unit,
    onServiceSelected: (String) -> Unit
) {
    var showAllServices by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .verticalScroll(rememberScrollState())
    ) {
        // Header (Clean text header matching the screenshot with Wallet and Notification buttons restored)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = StatusBarHeight + 16.dp, start = SpaceMD, end = SpaceMD, bottom = SpaceSM),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Selamat datang kembali,",
                    fontSize = 14.sp,
                    fontFamily = BodyFont,
                    fontWeight = FontWeight.Normal,
                    color = TextMuted
                )
                Text(
                    text = InMemoryDatabase.currentUser.nama.uppercase(),
                    fontSize = 20.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 0.5.sp
                )
            }
            
            // Restored Wallet & Notification buttons on the right side
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SpaceXS)
            ) {
                // Wallet button
                IconButton(onClick = onWallet) {
                    Icon(
                        imageVector = Icons.Default.AccountBalanceWallet,
                        contentDescription = "Wallet",
                        tint = Sand,
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Notification bell with badge "3"
                IconButton(onClick = onNotifications) {
                    BadgedBox(badge = {
                        Badge(containerColor = Error) {
                            Text("3", style = MaterialTheme.typography.labelSmall, color = Canvas)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = TextPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Search bar (Pill-shaped with yellow navigation icon button)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(30.dp))
                .clickable(onClick = onSearch)
                .padding(start = 16.dp, top = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Mau ke mana?",
                    fontSize = 15.sp,
                    fontFamily = BodyFont,
                    color = TextMuted,
                    modifier = Modifier.weight(1f)
                )
                // Yellow navigation button
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFE600)) // Figma/Maxim Yellow
                ) {
                    Icon(
                        Icons.Default.NearMe,
                        contentDescription = "GPS Navigate",
                        tint = TextPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(SpaceMD))

        // Map placeholder (HomeStyle Dark Grid Map with dots)
        MapPlaceholder(
            isHomeStyle = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = SpaceMD)
        )

        Spacer(Modifier.height(SpaceLG))

        // Service header (LAYANAN | LIHAT SEMUA)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "LAYANAN",
                fontSize = 14.sp,
                fontFamily = DisplayFont,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = if (showAllServices) "SEDIKIT" else "LIHAT SEMUA",
                fontSize = 11.sp,
                fontFamily = MonoFont,
                fontWeight = FontWeight.Bold,
                color = Blue,
                letterSpacing = 1.5.sp,
                modifier = Modifier.clickable { showAllServices = !showAllServices }
            )
        }
        
        Spacer(Modifier.height(SpaceSM))

        // Service grid layout
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            verticalArrangement = Arrangement.spacedBy(SpaceMD)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpaceMD)
            ) {
                ServiceCard(
                    icon = Icons.Default.TwoWheeler, // Match the motorcycle icon
                    label = "BIKE",
                    subtitle = "OJEK CEPAT",
                    priceLabel = "MULAI RP 8.000",
                    accentColor = Color(0xFFFFD600), // Yellow accent
                    priceColor = MaximDarkGold,
                    tintBg = YellowLight,
                    onClick = {
                        onServiceSelected("BIKE")
                        onSearch()
                    },
                    modifier = Modifier.weight(1f)
                )
                ServiceCard(
                    icon = Icons.Default.DirectionsCar,
                    label = "CAR",
                    subtitle = "TAKSI NYAMAN",
                    priceLabel = "MULAI RP 35.000",
                    accentColor = Color(0xFFFFD600), // Yellow accent
                    priceColor = MaximDarkGold,
                    tintBg = YellowLight,
                    onClick = {
                        onServiceSelected("CAR")
                        onSearch()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SpaceMD)
            ) {
                ServiceCard(
                    icon = Icons.Default.Inventory, // Box/package icon
                    label = "DELIVERY",
                    subtitle = "KIRIM BARANG",
                    priceLabel = "MULAI RP 12.000",
                    accentColor = Blue, // Blue accent
                    priceColor = Blue,
                    tintBg = BlueLight,
                    onClick = {
                        onServiceSelected("BIKE")
                        onSearch()
                    },
                    modifier = Modifier.weight(1f)
                )
                ServiceCard(
                    icon = Icons.Default.Restaurant, // Fork/knife icon
                    label = "FOOD",
                    subtitle = "PESAN MAKAN",
                    priceLabel = "MULAI RP 15.000",
                    accentColor = Terracotta, // Brown/Terracotta accent
                    priceColor = Terracotta,
                    tintBg = TerracottaLight,
                    onClick = {
                        onServiceSelected("BIKE")
                        onSearch()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            if (showAllServices) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpaceMD)
                ) {
                    ServiceCard(
                        icon = Icons.Default.LocalShipping,
                        label = "CARGO",
                        subtitle = "KIRIM BESAR",
                        priceLabel = "MULAI RP 75.000",
                        accentColor = Sand,
                        priceColor = Sand,
                        tintBg = SandLight,
                        onClick = {
                            onServiceSelected("CAR")
                            onSearch()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    ServiceCard(
                        icon = Icons.Default.Home,
                        label = "CLEANING",
                        subtitle = "JASA BERSIH",
                        priceLabel = "MULAI RP 40.000",
                        accentColor = Purple,
                        priceColor = Purple,
                        tintBg = PurpleLight,
                        onClick = {
                            onServiceSelected("BIKE")
                            onSearch()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(Modifier.height(SpaceLG))

        // Promo header (PROMO | LIHAT SEMUA)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "PROMO",
                fontSize = 14.sp,
                fontFamily = DisplayFont,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "LIHAT SEMUA",
                fontSize = 11.sp,
                fontFamily = MonoFont,
                fontWeight = FontWeight.Bold,
                color = Blue,
                letterSpacing = 1.5.sp,
                modifier = Modifier.clickable { onSeeAllPromos() }
            )
        }
        
        Spacer(Modifier.height(SpaceSM))

        // Promo banner carousel
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
private fun PromoBanner(
    title: String,
    subtitle: String,
    bg: Color,
    textColor: Color
) {
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
            Text(title.uppercase(), fontSize = 16.sp, fontFamily = DisplayFont, fontWeight = FontWeight.Bold, color = textColor)
            Text(subtitle, fontSize = 12.sp, fontFamily = BodyFont, color = textColor.copy(alpha = 0.8f))
        }
    }
}

