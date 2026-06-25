package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun SummaryScreen(onClose: () -> Unit, onRate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .verticalScroll(rememberScrollState())
            .padding(SpaceLG),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(StatusBarHeight))

        // Success icon
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(GreenLight)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Green, modifier = Modifier.size(48.dp))
        }
        Spacer(Modifier.height(SpaceLG))

        Text("PERJALANAN SELESAI", style = MaterialTheme.typography.headlineLarge, color = TextPrimary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(SpaceXS))
        Text("Terima kasih telah menggunakan Maxim!", style = MaterialTheme.typography.bodyMedium, color = TextBody, textAlign = TextAlign.Center)

        Spacer(Modifier.height(SpaceLG))

        // Trip details card
        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = Surface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(SpaceMD)) {
                Text("DETAIL PERJALANAN", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Spacer(Modifier.height(SpaceSM))

                DetailRow("Rute", "Lokasi saat ini → Duta Mall")
                DetailRow("Jarak", "8.4 km")
                DetailRow("Durasi", "23 menit")
                DetailRow("Kendaraan", "Economy Bike")
                DetailRow("Driver", "Ahmad Suryadi")

                Spacer(Modifier.height(SpaceSM))
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                Spacer(Modifier.height(SpaceSM))

                Text("RINCIAN PEMBAYARAN", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Spacer(Modifier.height(SpaceXS))

                PaymentRow("Tarif dasar", "Rp 12.000")
                PaymentRow("Biaya jarak", "Rp 3.000")
                PaymentRow("Diskon promo", "- Rp 0", Green)

                Spacer(Modifier.height(SpaceXS))
                HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
                Spacer(Modifier.height(SpaceXS))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Dibayar", style = MaterialTheme.typography.labelLarge, color = MaximDarkGold)
                    Text("Rp 15.000", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                }
                Spacer(Modifier.height(SpaceXXS))
                Text("Tunai", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            }
        }

        Spacer(Modifier.height(SpaceLG))

        PrimaryButton("Beri Rating", onClick = onRate)
        Spacer(Modifier.height(SpaceSM))
        SecondaryButton("Kembali ke Beranda", onClick = onClose)
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceXXS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = TextBody)
        Text(value, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
    }
}

@Composable
private fun PaymentRow(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color = TextPrimary) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = SpaceXXS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = TextBody)
        Text(value, style = MaterialTheme.typography.bodySmall, color = valueColor)
    }
}
