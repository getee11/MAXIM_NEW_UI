package com.example.maxim_project.ui.screens

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
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MapPlaceholder
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun TrackingScreen(
    onBack: () -> Unit,
    onChat: () -> Unit,
    onCS: () -> Unit,
    onReport: () -> Unit,
    onDone: () -> Unit
) {
    var status by remember { mutableIntStateOf(0) }
    val statuses = listOf(
        "Driver menuju lokasi jemput",
        "Driver tiba — silahkan naik",
        "Dalam perjalanan"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(
            title = "Live Tracking",
            onBack = onBack,
            rightIcon = Icons.Default.Warning,
            onRightClick = onReport
        )

        // Map with driver
        MapPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            showDriverIcon = true,
            showRoute = true
        )

        // Status label
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaximYellow)
                .padding(SpaceSM),
            contentAlignment = Alignment.Center
        ) {
            Text(
                statuses[status],
                style = MaterialTheme.typography.labelMedium,
                color = TextPrimary
            )
        }

        // Driver info card
        Card(
            shape = RoundedCornerShape(topStart = RadiusBub, topEnd = RadiusBub),
            colors = CardDefaults.cardColors(containerColor = Canvas),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(SpaceMD)) {
                // Driver profile row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(YellowLight)
                            .border(2.dp, MaximYellow, CircleShape)
                    ) {
                        Text("AS", style = MaterialTheme.typography.titleMedium, color = MaximDarkGold)
                    }
                    Spacer(Modifier.width(SpaceSM))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Ahmad Suryadi", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = MaximGold, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(SpaceXXS))
                            Text("4.9", style = MaterialTheme.typography.labelSmall, color = TextBody)
                            Spacer(Modifier.width(SpaceXS))
                            Text("•", color = TextMuted)
                            Spacer(Modifier.width(SpaceXS))
                            Text("Honda Vario 150", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                        }
                    }
                }

                Spacer(Modifier.height(SpaceXS))

                // Vehicle plate
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(RadiusXS))
                        .background(Surface)
                        .padding(horizontal = SpaceSM, vertical = SpaceXXS)
                ) {
                    Text("DA 1234 AB", style = MaterialTheme.typography.labelMedium, color = TextPrimary)
                }

                Spacer(Modifier.height(SpaceSM))

                // ETA
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = Blue, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(SpaceXXS))
                        Text("ETA: 5 menit", style = MaterialTheme.typography.labelSmall, color = Blue)
                    }
                    Text("Rp 15.000", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                }

                Spacer(Modifier.height(SpaceSM))
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                Spacer(Modifier.height(SpaceSM))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionBtn(Icons.Default.Phone, "Telepon", Green) { }
                    ActionBtn(Icons.Default.Chat, "Chat", Blue) { onChat() }
                    ActionBtn(Icons.Default.Share, "Bagikan", Purple) { }
                    ActionBtn(Icons.Default.Warning, "SOS", Error) { onReport() }
                }

                Spacer(Modifier.height(SpaceSM))

                // Simulate journey progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                ) {
                    PrimaryButton(
                        text = if (status < 2) "Status →" else "Selesai",
                        onClick = {
                            if (status < 2) status++ else onDone()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionBtn(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, tint: androidx.compose.ui.graphics.Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(tint.copy(alpha = 0.1f))
            ) {
                Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(20.dp))
            }
        }
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextBody)
    }
}
