package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private data class VehicleOption(
    val icon: ImageVector,
    val name: String,
    val desc: String,
    val eta: String,
    val price: String,
    val accent: Color,
    val tintBg: Color
)

private val VEHICLES = listOf(
    VehicleOption(Icons.Default.TwoWheeler, "Economy Bike", "1 penumpang, helm disediakan", "3 min", "Rp 15.000", MaximYellow, YellowLight),
    VehicleOption(Icons.Default.DirectionsCar, "Economy Car", "4 penumpang, AC", "5 min", "Rp 32.000", MaximGold, GoldLight),
    VehicleOption(Icons.Default.DirectionsCar, "Comfort Car", "4 penumpang, premium", "7 min", "Rp 45.000", Blue, BlueLight),
    VehicleOption(Icons.Default.AirportShuttle, "Minivan", "6 penumpang, bagasi besar", "10 min", "Rp 65.000", Purple, PurpleLight),
    VehicleOption(Icons.Default.LocalShipping, "Kurir Motor", "Paket s.d. 20kg", "4 min", "Rp 18.000", Green, GreenLight),
)

@Composable
fun VehicleScreen(onBack: () -> Unit, onNext: () -> Unit) {
    var selectedIdx by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Pilih Kendaraan", onBack = onBack)

        // Route summary
        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMD)
        ) {
            Row(
                modifier = Modifier.padding(SpaceSM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(Green))
                        Spacer(Modifier.width(SpaceXS))
                        Text("Lokasi saat ini", style = MaterialTheme.typography.bodySmall, color = TextBody)
                    }
                    Spacer(Modifier.height(SpaceXXS))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(Error))
                        Spacer(Modifier.width(SpaceXS))
                        Text("Duta Mall", style = MaterialTheme.typography.bodySmall, color = TextBody)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("8.4 km", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    Text("~23 min", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                }
            }
        }

        // Vehicle list
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = SpaceMD),
            verticalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            itemsIndexed(VEHICLES) { i, v ->
                val selected = i == selectedIdx
                val shape = RoundedCornerShape(RadiusMD)
                Card(
                    shape = shape,
                    colors = CardDefaults.cardColors(containerColor = if (selected) v.tintBg else Canvas),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 2.dp else 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (selected) Modifier.border(1.5.dp, v.accent, shape) else Modifier.border(1.dp, Hairline, shape)
                        )
                        .clickable { selectedIdx = i }
                ) {
                    Row(
                        modifier = Modifier.padding(SpaceMD),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(RadiusSM))
                                .background(if (selected) v.accent.copy(alpha = 0.15f) else Surface)
                        ) {
                            Icon(v.icon, contentDescription = null, tint = v.accent, modifier = Modifier.size(26.dp))
                        }
                        Spacer(Modifier.width(SpaceSM))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(v.name, style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                            Text(v.desc, style = MaterialTheme.typography.bodySmall, color = TextMuted)
                            Spacer(Modifier.height(SpaceXXS))
                            Text("ETA: ${v.eta}", style = MaterialTheme.typography.labelSmall, color = v.accent)
                        }
                        Text(v.price, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    }
                }
            }
        }

        // Bottom: price + book
        Column(modifier = Modifier.padding(SpaceMD)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total Estimasi", style = MaterialTheme.typography.bodyMedium, color = TextBody)
                Text(
                    VEHICLES[selectedIdx].price,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
            }
            Spacer(Modifier.height(SpaceSM))
            PrimaryButton(
                text = "Pesan Sekarang",
                onClick = onNext,
                color = VEHICLES[selectedIdx].accent,
                textColor = if (VEHICLES[selectedIdx].accent == MaximYellow) TextPrimary else Canvas
            )
        }
    }
}
