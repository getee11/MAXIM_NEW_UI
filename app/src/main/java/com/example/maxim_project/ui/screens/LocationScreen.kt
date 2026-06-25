package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private val SUGGESTIONS = listOf(
    "Duta Mall, Jl. A. Yani Km 2",
    "Universitas Lambung Mangkurat",
    "RS Ulin Banjarmasin",
    "Bandara Syamsudin Noor",
    "Pasar Baru Banjarmasin",
    "Masjid Raya Sabilal Muhtadin",
)

private val RECENT = listOf(
    "Kohy Coffee, Jl. Veteran",
    "Kampus FKIP ULM",
    "Kos Jl. Pramuka No. 12",
)

@Composable
fun LocationScreen(onBack: () -> Unit, onNext: () -> Unit) {
    var pickupText by remember { mutableStateOf("Lokasi saat ini") }
    var destText by remember { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Pilih Lokasi", onBack = onBack)

        Column(modifier = Modifier.padding(SpaceMD)) {
            // Pickup input
            OutlinedTextField(
                value = pickupText,
                onValueChange = { pickupText = it },
                label = { Text("Dari") },
                leadingIcon = {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Green)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { pickupText = "Lokasi saat ini" }) {
                        Icon(Icons.Default.MyLocation, contentDescription = "GPS", tint = Blue, modifier = Modifier.size(20.dp))
                    }
                },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(SpaceSM))

            // Destination input
            OutlinedTextField(
                value = destText,
                onValueChange = {
                    destText = it
                    showSuggestions = it.isNotEmpty()
                },
                label = { Text("Ke") },
                leadingIcon = {
                    Box(
                        Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Error)
                    )
                },
                placeholder = { Text("Mau ke mana?") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        HorizontalDivider(color = Hairline)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = SpaceXS)
        ) {
            // Recent locations
            if (!showSuggestions) {
                item {
                    Text(
                        "RIWAYAT",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)
                    )
                }
                items(RECENT) { loc ->
                    LocationItem(loc, Icons.Default.History) {
                        destText = loc
                        showSuggestions = false
                    }
                }
                item { Spacer(Modifier.height(SpaceMD)) }
                item {
                    Text(
                        "SARAN LOKASI",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)
                    )
                }
            }

            // Suggestions
            val list = if (showSuggestions) {
                SUGGESTIONS.filter { it.contains(destText, ignoreCase = true) }
            } else SUGGESTIONS

            items(list) { loc ->
                LocationItem(loc, Icons.Default.LocationOn) {
                    destText = loc
                    showSuggestions = false
                }
            }
        }

        // Confirm button
        Box(modifier = Modifier.padding(SpaceMD)) {
            PrimaryButton(
                text = "Konfirmasi Lokasi",
                onClick = onNext,
                enabled = destText.isNotEmpty()
            )
        }
    }
}

@Composable
private fun LocationItem(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = SpaceMD, vertical = SpaceSM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(SpaceSM))
        Text(text, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
    }
}
