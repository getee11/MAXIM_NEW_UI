package com.example.maxim_project.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

@Composable
fun ProfileTab(
    onFAQ: () -> Unit,
    onCS: () -> Unit,
    onWallet: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(StatusBarHeight + SpaceLG))

        // Profile header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(YellowLight)
                    .border(2.dp, MaximYellow, CircleShape)
            ) {
                Text("LK", style = MaterialTheme.typography.headlineLarge, color = MaximDarkGold)
            }
            Spacer(Modifier.height(SpaceSM))
            Text("Gt. Muhammad Luthfi Kamil", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            Spacer(Modifier.height(SpaceXXS))
            Text("+62 812 3456 7890", style = MaterialTheme.typography.bodyMedium, color = TextBody)
            Spacer(Modifier.height(SpaceXS))
            // Referral code
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(RadiusXS))
                    .background(PurpleLight)
                    .padding(horizontal = SpaceSM, vertical = SpaceXXS)
            ) {
                Text("Referral: LUTHFI2026", style = MaterialTheme.typography.labelSmall, color = Purple)
            }
        }

        Spacer(Modifier.height(SpaceLG))

        // Menu items
        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Canvas),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
        ) {
            Column {
                ProfileMenuItem(Icons.Default.Edit, "Edit Profil", TextPrimary) { }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.AccountBalanceWallet, "Metode Pembayaran", Sand) { onWallet() }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.LocationOn, "Alamat Tersimpan", Blue) { }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.Notifications, "Notifikasi", MaximGold) { }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.Language, "Bahasa Aplikasi", Green) { }
            }
        }

        Spacer(Modifier.height(SpaceMD))

        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Canvas),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
        ) {
            Column {
                ProfileMenuItem(Icons.AutoMirrored.Filled.HelpOutline, "Bantuan & FAQ", Blue) { onFAQ() }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.Headphones, "Hubungi Support", Terracotta) { onCS() }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.Shield, "Kebijakan Privasi", TextMuted) { }
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                ProfileMenuItem(Icons.Default.Info, "Tentang Aplikasi", TextMuted) { }
            }
        }

        Spacer(Modifier.height(SpaceMD))

        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Canvas),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
        ) {
            ProfileMenuItem(Icons.AutoMirrored.Filled.Logout, "Keluar", Error) { onLogout() }
        }

        Spacer(Modifier.height(SpaceLG))
        Text(
            "Maxim v1.0.0",
            style = MaterialTheme.typography.labelSmall,
            color = TextDim,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = BottomBarHeight + SpaceLG)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ProfileMenuItem(icon: ImageVector, label: String, tint: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(SpaceMD),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(RadiusXS))
                .background(tint.copy(alpha = 0.1f))
        ) {
            Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(SpaceSM))
        Text(label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
    }
}
