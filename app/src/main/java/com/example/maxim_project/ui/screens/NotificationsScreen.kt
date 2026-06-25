package com.example.maxim_project.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Headphones
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.theme.*

private data class NotifItem(
    val icon: ImageVector,
    val title: String,
    val desc: String,
    val time: String,
    val accent: Color,
    val tintBg: Color,
    val read: Boolean
)

private val INITIAL_NOTIFS = listOf(
    NotifItem(Icons.Outlined.LocalOffer, "Promo spesial untukmu!", "Dapatkan 50% diskon perjalanan dengan kode MAXFIRST50.", "2 MNT LALU", Color(0xFF6C5A25), YellowLight, false),
    NotifItem(Icons.Outlined.DirectionsCar, "Driver sedang menuju lokasimu", "Budi Santoso (B 1234 KLM) dalam perjalanan menjemputmu.", "15 MNT LALU", Color(0xFF3B82F6), BlueLight, false),
    NotifItem(Icons.Outlined.CheckCircle, "Perjalanan selesai", "Perjalanan ke Kelapa Gading selesai. Rp 22.500 ditagih.", "1 JAM LALU", Color(0xFF22C55E), GreenLight, true),
    NotifItem(Icons.Outlined.CardGiftcard, "Flash sale food delivery", "Gratis ongkir untuk pemesanan makanan hari ini!", "3 JAM LALU", Color(0xFF8C5C4C), TerracottaLight, true),
    NotifItem(Icons.Outlined.Headphones, "CS membalas pesanmu", "Laporan #TKT-2821 telah diproses. Driver mendapat peringatan.", "KEMARIN", Color(0xFF3B82F6), BlueLight, true),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var notifs by remember { mutableStateOf(INITIAL_NOTIFS) }
    
    // Count unread
    val unreadCount = notifs.count { !it.read }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        // Custom Navbar matching screenshot
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "NOTIFIKASI",
                    fontSize = 16.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 1.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        tint = TextPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            actions = {
                // DoneAll double checkmark icon in green
                IconButton(onClick = {
                    notifs = notifs.map { it.copy(read = true) }
                    Toast.makeText(context, "Semua notifikasi ditandai dibaca", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DoneAll,
                        contentDescription = "Mark all read",
                        tint = Color(0xFF22C55E), // Green
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier.border(1.dp, Hairline.copy(alpha = 0.3f))
        )

        // Subheader Status Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$unreadCount BELUM DIBACA",
                fontSize = 11.sp,
                fontFamily = MonoFont,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
            
            if (unreadCount > 0) {
                Text(
                    text = "TANDAI SEMUA DIBACA",
                    fontSize = 11.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B82F6), // Blue
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.clickable {
                        notifs = notifs.map { it.copy(read = true) }
                        Toast.makeText(context, "Semua notifikasi ditandai dibaca", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        // Notifications List
        LazyColumn(
            contentPadding = PaddingValues(start = SpaceMD, top = 0.dp, end = SpaceMD, bottom = SpaceLG),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(notifs) { idx, notif ->
                val shape = RoundedCornerShape(RadiusMD)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape)
                        .background(Color.White)
                        .border(1.dp, Hairline.copy(alpha = 0.5f), shape)
                        .clickable {
                            // Mark single notification read
                            if (!notif.read) {
                                notifs = notifs.mapIndexed { i, item ->
                                    if (i == idx) item.copy(read = true) else item
                                }
                            }
                            // Contextual Toast feedback based on notification type
                            val feedbackMsg = when {
                                notif.title.contains("Promo", ignoreCase = true) || notif.title.contains("sale", ignoreCase = true) -> "Melihat detail promo..."
                                notif.title.contains("Driver", ignoreCase = true) -> "Melihat status driver..."
                                notif.title.contains("selesai", ignoreCase = true) -> "Melihat detail perjalanan..."
                                notif.title.contains("CS", ignoreCase = true) || notif.title.contains("membalas", ignoreCase = true) -> "Membuka percakapan CS..."
                                else -> "Membuka notifikasi..."
                            }
                            Toast.makeText(context, feedbackMsg, Toast.LENGTH_SHORT).show()
                        }
                ) {
                    // Accent border on the left
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(notif.accent)
                            .align(Alignment.CenterStart)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Circle Category Icon container
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(notif.tintBg)
                        ) {
                            Icon(
                                imageVector = notif.icon,
                                contentDescription = null,
                                tint = notif.accent,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(Modifier.width(16.dp))

                        // Text Content Column
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = notif.title,
                                fontSize = 13.sp,
                                fontFamily = DisplayFont,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = notif.desc,
                                fontSize = 11.sp,
                                fontFamily = BodyFont,
                                color = TextBody,
                                lineHeight = 15.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = notif.time,
                                fontSize = 10.sp,
                                fontFamily = MonoFont,
                                color = TextMuted
                            )
                        }

                        // Unread dot indicator on the far right
                        if (!notif.read) {
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(notif.accent)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }
    }
}
