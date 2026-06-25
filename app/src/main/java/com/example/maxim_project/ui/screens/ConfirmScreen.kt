package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun ConfirmScreen(onBack: () -> Unit, onConfirm: () -> Unit) {
    var note by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Konfirmasi", onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(SpaceMD)
        ) {
            // Trip summary
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(SpaceMD)) {
                    Text("RINGKASAN PERJALANAN", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    Spacer(Modifier.height(SpaceSM))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(Green))
                        Spacer(Modifier.width(SpaceXS))
                        Text("Lokasi saat ini", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                    Box(Modifier.padding(start = 3.dp).width(2.dp).height(16.dp).background(Hairline))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(Error))
                        Spacer(Modifier.width(SpaceXS))
                        Text("Duta Mall, Jl. A. Yani Km 2", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    }
                    Spacer(Modifier.height(SpaceSM))
                    HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                    Spacer(Modifier.height(SpaceSM))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Economy Bike", style = MaterialTheme.typography.labelSmall, color = MaximDarkGold)
                        Text("Rp 15.000", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                    }
                }
            }

            Spacer(Modifier.height(SpaceMD))

            // Special requests
            Text("PERMINTAAN KHUSUS", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(Modifier.height(SpaceXS))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("Catatan untuk driver (opsional)") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                minLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(SpaceMD))

            // Payment method
            Text("METODE PEMBAYARAN", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(Modifier.height(SpaceXS))

            val methods = listOf(
                Triple(Icons.Default.Money, "Tunai", MaximGold),
                Triple(Icons.Default.AccountBalanceWallet, "Maxim Pay", Sand),
                Triple(Icons.Default.CreditCard, "Kartu", Blue)
            )
            methods.forEachIndexed { i, (icon, label, accent) ->
                val sel = i == paymentMethod
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(RadiusSM))
                        .then(
                            if (sel) Modifier.background(accent.copy(alpha = 0.08f)) else Modifier
                        )
                        .clickable { paymentMethod = i }
                        .padding(SpaceSM),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = sel,
                        onClick = { paymentMethod = i },
                        colors = RadioButtonDefaults.colors(selectedColor = accent)
                    )
                    Spacer(Modifier.width(SpaceXS))
                    Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(SpaceXS))
                    Text(label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    if (label == "Maxim Pay") {
                        Spacer(Modifier.weight(1f))
                        Text("Rp 150.000", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                    }
                }
            }

            Spacer(Modifier.height(SpaceMD))

            // Price breakdown
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = YellowLight),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(SpaceMD)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tarif dasar", style = MaterialTheme.typography.bodySmall, color = TextBody)
                        Text("Rp 12.000", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    }
                    Spacer(Modifier.height(SpaceXXS))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Biaya jarak", style = MaterialTheme.typography.bodySmall, color = TextBody)
                        Text("Rp 3.000", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
                    }
                    Spacer(Modifier.height(SpaceXS))
                    HorizontalDivider(color = MaximYellow.copy(alpha = 0.3f))
                    Spacer(Modifier.height(SpaceXS))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("TOTAL", style = MaterialTheme.typography.labelLarge, color = MaximDarkGold)
                        Text("Rp 15.000", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                    }
                }
            }
        }

        // Bottom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMD),
            horizontalArrangement = Arrangement.spacedBy(SpaceSM)
        ) {
            SecondaryButton("Batal", onClick = onBack, modifier = Modifier.weight(1f))
            PrimaryButton("Konfirmasi", onClick = onConfirm, modifier = Modifier.weight(2f))
        }
    }
}
