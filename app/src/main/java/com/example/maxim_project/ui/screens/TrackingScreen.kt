package com.example.maxim_project.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MapPlaceholder
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maxim_project.data.viewmodel.ReportViewModel

@Composable
fun TrackingScreen(
    onBack: () -> Unit,
    onChat: () -> Unit,
    onCS: () -> Unit,
    onReport: () -> Unit,
    onDone: () -> Unit,
    reportViewModel: ReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val driver = reportViewModel.currentDriver
    val trip = reportViewModel.currentTrip

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        // Map with driver and float overlays
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
        ) {
            MapPlaceholder(
                modifier = Modifier.fillMaxSize(),
                showDriverIcon = false,
                showRoute = true
            )

            // Top Floating Back Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = StatusBarHeight + 16.dp, start = 16.dp)
                    .size(44.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape)
                    .background(Color.White, CircleShape)
                    .clickable(onClick = onBack)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Top Floating CS Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = StatusBarHeight + 16.dp, end = 16.dp)
                    .size(44.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape)
                    .background(Color.White, CircleShape)
                    .clickable(onClick = onCS)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Headset,
                    contentDescription = "Contact CS",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Bottom Left Floating Status Pill
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .border(1.dp, Color(0xFFFFE600), RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFFFD600), CircleShape)
                    )
                    Text(
                        text = "DRIVER MENUJU LOKASI JEMPUT",
                        fontSize = 9.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Bottom Right Floating ETA Card
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "5",
                        fontSize = 20.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "MENIT",
                        fontSize = 8.sp,
                        fontFamily = MonoFont,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // Driver details card & Action buttons
        Card(
            shape = RoundedCornerShape(topStart = RadiusBub, topEnd = RadiusBub),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, Hairline.copy(alpha = 0.5f)),
                    RoundedCornerShape(topStart = RadiusBub, topEnd = RadiusBub)
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Driver profile row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .background(Color.White, CircleShape)
                            .border(2.dp, Color(0xFFFFE600), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = driver.namaDriver.uppercase(),
                            fontSize = 16.sp,
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                                repeat(5) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD600),
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = String.format("%.2f", driver.ratingRataRata),
                                fontSize = 11.sp,
                                fontFamily = MonoFont,
                                color = TextMuted,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "ECONOMY RIDE",
                            fontSize = 9.sp,
                            fontFamily = MonoFont,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = driver.platNomor,
                            fontSize = 13.sp,
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Driver contact / actions row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DriverActionButton(
                        icon = Icons.Default.Phone,
                        label = "TELEPON",
                        tintColor = Green,
                        onClick = {
                            Toast.makeText(context, "Menghubungi ${driver.namaDriver}...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    DriverActionButton(
                        icon = Icons.AutoMirrored.Filled.Chat,
                        label = "CHAT",
                        tintColor = Blue,
                        onClick = onChat,
                        modifier = Modifier.weight(1f)
                    )
                    DriverActionButton(
                        icon = Icons.Default.Share,
                        label = "BAGIKAN",
                        tintColor = TextMuted,
                        onClick = {
                            Toast.makeText(context, "Membagikan info perjalanan...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Hairline.copy(alpha = 0.3f))
                Spacer(Modifier.height(16.dp))

                // Bottom CTA action buttons
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Laporkan button
                    Button(
                        onClick = onReport,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFF0F0),
                            contentColor = Color(0xFFEF4444)
                        ),
                        border = BorderStroke(1.dp, Color(0xFFFCA5A5)),
                        shape = RoundedCornerShape(RadiusSM),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text("LAPORKAN DRIVER", fontSize = 13.sp, fontFamily = DisplayFont, fontWeight = FontWeight.Bold)
                    }

                    // Emergency / SOS button
                    Button(
                        onClick = {
                            Toast.makeText(context, "🚨 Menghubungi layanan darurat 112...", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(RadiusSM),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("DARURAT / SOS", fontSize = 13.sp, fontFamily = DisplayFont, fontWeight = FontWeight.Bold)
                    }

                    // Done / Demo complete button
                    PrimaryButton(
                        text = "SELESAI (DEMO)",
                        onClick = onDone,
                        color = Color(0xFFFFE600), // Maxim Yellow
                        textColor = TextPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun DriverActionButton(
    icon: ImageVector,
    label: String,
    tintColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = tintColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 9.sp,
                fontFamily = MonoFont,
                fontWeight = FontWeight.Bold,
                color = TextMuted,
                letterSpacing = 0.5.sp
            )
        }
    }
}
