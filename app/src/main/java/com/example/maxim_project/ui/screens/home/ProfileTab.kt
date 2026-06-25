package com.example.maxim_project.ui.screens.home

import android.widget.Toast
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.theme.*
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.maxim_project.data.InMemoryDatabase

@Composable
fun ProfileTab(
    walletBalance: Int,
    onFAQ: () -> Unit,
    onCS: () -> Unit,
    onWallet: () -> Unit,
    onNotifications: () -> Unit,
    onLocation: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .verticalScroll(rememberScrollState())
    ) {
        // Diagonal Gradient Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFFDE7), Canvas)
                    )
                )
                .padding(top = StatusBarHeight + 24.dp, start = SpaceMD, end = SpaceMD, bottom = SpaceMD)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Large Avatar with Yellow BG
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFE600)) // Yellow BG
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Avatar",
                        tint = TextPrimary,
                        modifier = Modifier.size(44.dp)
                    )
                }

                Spacer(Modifier.width(20.dp))

                // Profile Details
                Column {
                    Text(
                        text = InMemoryDatabase.currentUser.nama.uppercase(),
                        fontSize = 20.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "+62 812-XXXX-XXXX",
                        fontSize = 13.sp,
                        fontFamily = BodyFont,
                        color = TextMuted
                    )
                    Spacer(Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Assignment,
                            contentDescription = "Trips",
                            tint = TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "47 perjalanan",
                            fontSize = 12.sp,
                            fontFamily = BodyFont,
                            color = TextMuted
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Card - MAXIM WALLET
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
                .clip(RoundedCornerShape(RadiusMD))
                .background(Color(0xFFFFD600)) // Yellow Card BG
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "MAXIM WALLET",
                        fontSize = 10.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary.copy(alpha = 0.8f),
                        letterSpacing = 1.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Rp " + java.text.DecimalFormat("#,###").format(walletBalance).replace(',', '.'),
                        fontSize = 24.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                // TOP UP button
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(90.dp)
                        .height(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, MaximDarkGold.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .background(Color(0x22FFFFFF))
                        .clickable(onClick = onWallet)
                ) {
                    Text(
                        text = "TOP UP",
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Blue Button - HUBUNGI CS
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF3B82F6)) // Blue BG
                    .clickable(onClick = onCS)
            ) {
                Text(
                    text = "HUBUNGI CS",
                    fontSize = 14.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // Individual card-style menus
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileCardItem(
                icon = Icons.Outlined.Person,
                title = "EDIT PROFIL",
                desc = "Ubah nama, foto, info personal",
                onClick = { Toast.makeText(context, "Fitur Edit Profil akan segera hadir!", Toast.LENGTH_SHORT).show() }
            )

            ProfileCardItem(
                icon = Icons.Outlined.Payment,
                title = "METODE PEMBAYARAN",
                desc = "Kelola kartu & dompet digital",
                onClick = onWallet
            )

            ProfileCardItem(
                icon = Icons.Outlined.LocationOn,
                title = "ALAMAT TERSIMPAN",
                desc = "Rumah, kantor, favorit",
                onClick = onLocation
            )

            ProfileCardItem(
                icon = Icons.Outlined.Notifications,
                title = "NOTIFIKASI",
                desc = "Atur preferensi pemberitahuan",
                onClick = onNotifications
            )

            ProfileCardItem(
                icon = Icons.Outlined.CardGiftcard,
                title = "KODE REFERRAL",
                desc = "Bagikan & dapatkan Rp 25.000",
                onClick = {
                    clipboardManager.setText(AnnotatedString("MAXIM2026"))
                    Toast.makeText(context, "Kode referral kamu: MAXIM2026 — disalin ke clipboard!", Toast.LENGTH_SHORT).show()
                }
            )

            ProfileCardItem(
                icon = Icons.Outlined.HelpOutline,
                title = "BANTUAN & FAQ",
                desc = "Pertanyaan umum dan dukungan",
                onClick = onFAQ
            )

            // Logout card
            ProfileCardItem(
                icon = Icons.Outlined.Logout,
                title = "KELUAR AKUN",
                desc = "Keluar dari sesi masuk aplikasi saat ini",
                onClick = onLogout,
                accentColor = Error
            )
        }

        Spacer(Modifier.height(SpaceLG))
        Text(
            text = "Maxim v1.0.0",
            fontSize = 11.sp,
            fontFamily = MonoFont,
            color = TextDim,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = BottomBarHeight + SpaceLG)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ProfileCardItem(
    icon: ImageVector,
    title: String,
    desc: String,
    onClick: () -> Unit,
    accentColor: Color = TextMuted
) {
    Card(
        shape = RoundedCornerShape(RadiusMD),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Surface)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (accentColor == Error) Error else TextBody,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = if (accentColor == Error) Error else TextPrimary
                )
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    fontFamily = BodyFont,
                    color = TextMuted
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
