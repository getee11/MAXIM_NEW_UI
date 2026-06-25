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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maxim_project.data.viewmodel.ReportViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

data class NotifItem(
    val icon: ImageVector,
    val title: String,
    val desc: String,
    val time: String,
    val accent: Color,
    val tintBg: Color,
    val isRead: Boolean,
    val originalIndex: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    reportViewModel: ReportViewModel = viewModel(),
    onBack: () -> Unit
) {
    val driver by reportViewModel.currentDriver.collectAsStateWithLifecycle()
    val driverName = driver?.namaDriver ?: "Sopir"
    val platNomor = driver?.platNomor ?: "B 1234 KLM"
    val context = LocalContext.current
    
    val dbNotifs by reportViewModel.notifications.collectAsStateWithLifecycle()
    
    val notifs = dbNotifs.mapIndexed { idx, n ->
        val isPromo = n.title.contains("Promo", true) || n.title.contains("sale", true)
        val isCS = n.title.contains("CS", true) || n.title.contains("Sistem", true) || n.title.contains("Selesai", true)
        val isDriver = n.title.contains("Driver", true)
        
        NotifItem(
            icon = when {
                isPromo -> Icons.Outlined.LocalOffer
                isCS -> Icons.Outlined.Headphones
                isDriver -> Icons.Outlined.DirectionsCar
                else -> Icons.Outlined.AccessTime
            },
            title = n.title,
            desc = n.desc,
            time = n.time,
            accent = when {
                isPromo -> MaximYellow
                isCS -> Color(0xFF3B82F6) // Blue
                isDriver -> Color(0xFF22C55E) // Green
                else -> Color(0xFF8B5CF6) // Purple
            },
            tintBg = when {
                isPromo -> YellowLight
                isCS -> BlueLight
                isDriver -> GreenLight
                else -> Color(0xFFEDE9FE)
            },
            isRead = n.isRead,
            originalIndex = idx
        )
    }

    // Count unread
    val unreadCount = dbNotifs.count { !it.isRead }

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
                    reportViewModel.markAllNotificationsRead()
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
                        reportViewModel.markAllNotificationsRead()
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
                            if (!notif.isRead) {
                                reportViewModel.markNotificationAsRead(dbNotifs[notif.originalIndex])
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
                        if (!notif.isRead) {
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
