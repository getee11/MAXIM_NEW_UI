package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MapPlaceholder
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.OrderData
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun OrderDetailScreen(
    order: OrderData,
    onBack: () -> Unit,
    onReorder: () -> Unit,
    onReport: () -> Unit,
    onDownloadReceipt: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDone = order.status == "Selesai"
    val statusColor = if (isDone) Green else Error
    val statusLabel = if (isDone) "SELESAI" else "DIBATALKAN"

    Scaffold(
        topBar = {
            MaximNavBar(
                title = "Detail Pesanan",
                onBack = onBack
            )
        },
        containerColor = Canvas,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = SpaceMD, vertical = SpaceSM)
            ) {
                // Status Badge
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SpaceXS),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.labelMedium,
                            color = statusColor
                        )
                    }
                    Text(
                        text = order.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(SpaceSM))

                // Map mini
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(RadiusMD))
                        .border(1.5.dp, Hairline, RoundedCornerShape(RadiusMD))
                ) {
                    MapPlaceholder(
                        modifier = Modifier.fillMaxSize(),
                        showRoute = true
                    )
                }

                Spacer(modifier = Modifier.height(SpaceMD))

                // Route Card
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = borderStroke(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(SpaceMD)) {
                        Text(
                            text = "RUTE PERJALANAN",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(SpaceSM))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            // Timeline line indicator
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Green)
                                )
                                Box(
                                    modifier = Modifier
                                        .width(1.5.dp)
                                        .height(32.dp)
                                        .background(Hairline)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Error)
                                )
                            }
                            Spacer(modifier = Modifier.width(SpaceSM))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = order.from,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(SpaceSM))
                                Text(
                                    text = order.to,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextPrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(SpaceMD))

                        // Service info row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                        ) {
                            // Service Type
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Surface, RoundedCornerShape(RadiusXS))
                                    .padding(vertical = SpaceSM),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("LAYANAN", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = order.type.uppercase(),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = order.accent
                                )
                            }
                            // Distance
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Surface, RoundedCornerShape(RadiusXS))
                                    .padding(vertical = SpaceSM),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("JARAK", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = order.distance,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = TextPrimary
                                )
                            }
                            // Duration
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Surface, RoundedCornerShape(RadiusXS))
                                    .padding(vertical = SpaceSM),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("DURASI", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = order.duration,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceSM))

                // Driver Info Card
                if (isDone) {
                    Card(
                        shape = RoundedCornerShape(RadiusMD),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = borderStroke(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(SpaceMD)) {
                            Text(
                                text = "INFORMASI DRIVER",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMuted
                            )
                            Spacer(modifier = Modifier.height(SpaceSM))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Surface)
                                        .border(2.dp, MaximYellow, CircleShape)
                                ) {
                                    Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = TextPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(SpaceMD))
                                Column {
                                    Text(
                                        text = order.driver,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = order.vehicle,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextMuted
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Cancelled Alert
                    Card(
                        shape = RoundedCornerShape(RadiusMD),
                        colors = CardDefaults.cardColors(containerColor = ErrorLight.copy(alpha = 0.3f)),
                        border = borderStroke(Error.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(SpaceMD)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                            ) {
                                Icon(
                                    Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Error,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "PESANAN DIBATALKAN",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Error
                                )
                            }
                            Spacer(modifier = Modifier.height(SpaceXS))
                            Text(
                                text = "Pesanan ini dibatalkan sebelum driver ditemukan atau sebelum perjalanan dimulai. Tidak ada biaya yang dikenakan.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextBody
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceSM))

                // Payment Breakdown Card
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = borderStroke(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(SpaceMD)) {
                        Text(
                            text = "RINCIAN PEMBAYARAN",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMuted
                        )
                        Spacer(modifier = Modifier.height(SpaceSM))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(SpaceXS),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Surface, RoundedCornerShape(RadiusXS))
                                .padding(horizontal = SpaceSM, vertical = SpaceXS)
                        ) {
                            Icon(
                                Icons.Default.Wallet,
                                contentDescription = null,
                                tint = MaximDarkGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Maxim Wallet",
                                style = MaterialTheme.typography.labelMedium,
                                color = TextPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(SpaceMD))

                        if (isDone) {
                            BreakdownRow(label = "Tarif dasar", value = order.price)
                            BreakdownRow(label = "Biaya layanan", value = "Rp 3.000")
                            HorizontalDivider(color = Hairline, modifier = Modifier.padding(vertical = SpaceSM))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Total Dibayar",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary
                                )
                                Text(
                                    text = order.price,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = TextPrimary
                                )
                            }
                        } else {
                            BreakdownRow(label = "Tidak ada biaya dikenakan", value = "Rp 0")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceLG))
            }

            // Bottom Actions Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Canvas)
                    .navigationBarsPadding()
            ) {
                HorizontalDivider(color = Hairline, thickness = 1.5.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SpaceMD, vertical = SpaceSM),
                    verticalArrangement = Arrangement.spacedBy(SpaceSM)
                ) {
                    if (isDone) {
                        PrimaryButton(
                            text = "Pesan Lagi",
                            onClick = onReorder
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(SpaceSM)
                    ) {
                        SecondaryButton(
                            text = "Unduh Struk",
                            onClick = onDownloadReceipt,
                            modifier = Modifier.weight(1f)
                        )
                        if (isDone) {
                            SecondaryButton(
                                text = "Laporkan",
                                onClick = onReport,
                                color = Error,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BreakdownRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
    }
}

@Composable
private fun borderStroke(color: Color = Hairline) = androidx.compose.foundation.BorderStroke(1.5.dp, color)
