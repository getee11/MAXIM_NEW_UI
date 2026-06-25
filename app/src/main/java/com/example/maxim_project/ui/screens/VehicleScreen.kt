package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    VehicleOption(Icons.Default.TwoWheeler, "BIKE ECONOMY", "CEPAT & HEMAT • 1 PENUMPANG", "~3 MNT", "Rp 18.000", MaximDarkGold, Color(0xFFFCF9DF)),
    VehicleOption(Icons.Default.TwoWheeler, "BIKE PLUS", "HELM STANDAR SNI • 1 PENUMPANG", "~2 MNT", "Rp 22.000", MaximDarkGold, Color(0xFFFCF9DF)),
    VehicleOption(Icons.Default.DirectionsCar, "CAR ECONOMY", "NYAMAN & TERJANGKAU • 4 PENUMPANG", "~6 MNT", "Rp 45.000", MaximDarkGold, Color(0xFFFCF9DF)),
    VehicleOption(Icons.Default.DirectionsCar, "CAR COMFORT", "AC DINGIN, PREMIUM • 4 PENUMPANG", "~5 MNT", "Rp 65.000", MaximDarkGold, Color(0xFFFCF9DF)),
    VehicleOption(Icons.Default.AirportShuttle, "MINIVAN", "KAPASITAS BESAR & LEGA • 6 PENUMPANG", "~8 MNT", "Rp 85.000", MaximDarkGold, Color(0xFFFCF9DF)),
)

@Composable
fun VehicleScreen(
    serviceType: String,
    onBack: () -> Unit,
    onNext: (String) -> Unit
) {
    val filteredVehicles = remember(serviceType) {
        if (serviceType == "BIKE") {
            VEHICLES.filter { it.name.contains("BIKE") }
        } else {
            VEHICLES.filter { it.name.contains("CAR") || it.name == "MINIVAN" }
        }
    }

    var selectedIdx by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(filteredVehicles) {
        if (selectedIdx >= filteredVehicles.size) {
            selectedIdx = 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "PILIH KENDARAAN", onBack = onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = SpaceMD, vertical = SpaceSM),
            verticalArrangement = Arrangement.spacedBy(SpaceSM)
        ) {
            // Route Card
            item {
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Indicators column
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(12.dp)
                        ) {
                            Box(Modifier.size(8.dp).clip(CircleShape).background(Green))
                            // Dashed vertical line
                            Box(
                                Modifier
                                    .width(1.dp)
                                    .height(20.dp)
                                    .background(Hairline)
                            )
                            Box(Modifier.size(8.dp).background(Error))
                        }
                        
                        Spacer(Modifier.width(16.dp))

                        // Addresses column
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Lokasi saat ini", fontSize = 13.sp, fontFamily = BodyFont, color = TextPrimary)
                            Spacer(Modifier.height(12.dp))
                            Text("Kelapa Gading, Jakarta Utara", fontSize = 13.sp, fontFamily = BodyFont, color = TextPrimary)
                        }

                        // Distance details
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                "12.3 KM",
                                fontSize = 11.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = Blue
                            )
                        }
                    }
                }
            }

            // Vehicles list
            itemsIndexed(filteredVehicles) { i, v ->
                val selected = i == selectedIdx
                val shape = RoundedCornerShape(RadiusMD)
                Card(
                    shape = shape,
                    colors = CardDefaults.cardColors(containerColor = if (selected) v.tintBg else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 2.dp else 0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (selected) Modifier.border(1.5.dp, v.accent, shape) else Modifier.border(1.dp, Hairline.copy(alpha = 0.5f), shape)
                        )
                        .clickable { selectedIdx = i }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(RadiusSM))
                                .background(Color.White)
                                .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusSM))
                        ) {
                            Icon(
                                v.icon,
                                contentDescription = v.name,
                                tint = TextPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = v.name,
                                fontSize = 15.sp,
                                fontFamily = DisplayFont,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = v.desc,
                                fontSize = 10.sp,
                                fontFamily = MonoFont,
                                color = TextMuted,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = v.price,
                                fontSize = 18.sp,
                                fontFamily = DisplayFont,
                                fontWeight = FontWeight.Bold,
                                color = if (selected) MaximDarkGold else TextPrimary
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = v.eta,
                                fontSize = 11.sp,
                                fontFamily = MonoFont,
                                color = TextMuted,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Bottom estimation & booking layout
        HorizontalDivider(color = Hairline.copy(alpha = 0.3f), thickness = 1.dp)
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(SpaceMD)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total estimasi",
                    fontSize = 13.sp,
                    fontFamily = BodyFont,
                    color = TextMuted
                )
                if (selectedIdx < filteredVehicles.size) {
                    Text(
                        text = filteredVehicles[selectedIdx].price,
                        fontSize = 26.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            PrimaryButton(
                text = "PESAN SEKARANG",
                onClick = { 
                    if (selectedIdx < filteredVehicles.size) {
                        onNext(filteredVehicles[selectedIdx].price)
                    } else {
                        onNext("Rp 0")
                    }
                },
                color = Color(0xFFFFE600), // Solid Yellow
                textColor = TextPrimary
            )
        }
    }
}
