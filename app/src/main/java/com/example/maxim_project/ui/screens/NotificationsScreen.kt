package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.theme.*

private data class NotifItem(val icon: ImageVector, val title: String, val desc: String, val time: String, val accent: Color, val read: Boolean)

private val NOTIFS = listOf(
    NotifItem(Icons.Default.LocalOffer, "Promo Spesial!", "Diskon 30% untuk perjalanan motor hari ini", "2 jam lalu", MaximYellow, false),
    NotifItem(Icons.Default.CheckCircle, "Perjalanan Selesai", "Perjalanan ke Duta Mall telah selesai. Berikan rating!", "3 jam lalu", Green, false),
    NotifItem(Icons.Default.AccountBalanceWallet, "Top Up Berhasil", "Saldo Maxim Pay bertambah Rp 100.000", "Kemarin", Sand, true),
    NotifItem(Icons.Default.Campaign, "Update Aplikasi", "Versi terbaru Maxim tersedia, perbarui sekarang", "2 hari lalu", Blue, true),
    NotifItem(Icons.Default.Star, "Rating Diterima", "Driver Ahmad memberikan rating 5 bintang untuk Anda", "3 hari lalu", MaximGold, true),
    NotifItem(Icons.Default.Security, "Keamanan Akun", "Login baru terdeteksi dari perangkat ASUS TUF", "1 minggu lalu", Error, true),
)

@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Notifikasi", onBack = onBack)

        LazyColumn(
            contentPadding = PaddingValues(SpaceMD),
            verticalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            items(NOTIFS) { notif ->
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(
                        containerColor = if (!notif.read) notif.accent.copy(alpha = 0.05f) else Canvas
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (!notif.read) 1.dp else 0.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(SpaceMD),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(RadiusSM))
                                .background(notif.accent.copy(alpha = 0.12f))
                        ) {
                            Icon(notif.icon, contentDescription = null, tint = notif.accent, modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(SpaceSM))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    notif.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextPrimary
                                )
                                if (!notif.read) {
                                    Box(
                                        Modifier
                                            .size(8.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(notif.accent)
                                    )
                                }
                            }
                            Spacer(Modifier.height(SpaceXXS))
                            Text(notif.desc, style = MaterialTheme.typography.bodySmall, color = TextBody)
                            Spacer(Modifier.height(SpaceXXS))
                            Text(notif.time, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }
                    }
                }
            }
        }
    }
}
