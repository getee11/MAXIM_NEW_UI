package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private data class TxItem(val icon: ImageVector, val title: String, val amount: String, val time: String, val accent: Color, val isIncome: Boolean)

private val TRANSACTIONS = listOf(
    TxItem(Icons.Default.RemoveCircle, "Economy Bike", "- Rp 15.000", "Hari ini, 14:55", Error, false),
    TxItem(Icons.Default.AddCircle, "Top Up", "+ Rp 100.000", "Kemarin, 10:20", Green, true),
    TxItem(Icons.Default.RemoveCircle, "Kurir Motor", "- Rp 22.000", "21 Jun, 12:05", Error, false),
    TxItem(Icons.Default.AddCircle, "Cashback Promo", "+ Rp 5.000", "20 Jun, 09:00", Green, true),
    TxItem(Icons.Default.RemoveCircle, "Economy Car", "- Rp 35.000", "19 Jun, 18:30", Error, false),
)

@Composable
fun WalletScreen(
    onBack: () -> Unit,
    walletBalance: Int,
    onTopUp: (Int) -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Maxim Pay", onBack = onBack)

        // Balance card
        Card(
            shape = RoundedCornerShape(RadiusMD),
            colors = CardDefaults.cardColors(containerColor = MaximYellow),
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceMD)
        ) {
            Column(
                modifier = Modifier.padding(SpaceLG),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("SALDO ANDA", style = MaterialTheme.typography.labelMedium, color = MaximDarkGold)
                Spacer(Modifier.height(SpaceXS))
                Text(
                    text = "Rp " + java.text.DecimalFormat("#,###").format(walletBalance).replace(',', '.'),
                    style = MaterialTheme.typography.displaySmall,
                    color = TextPrimary
                )
                Spacer(Modifier.height(SpaceMD))
                PrimaryButton(
                    text = "Top Up",
                    onClick = {
                        onTopUp(50000)
                        android.widget.Toast.makeText(context, "Top Up Berhasil! Saldo bertambah Rp 50.000", android.widget.Toast.LENGTH_SHORT).show()
                    },
                    color = TextPrimary,
                    textColor = Canvas,
                    modifier = Modifier.width(160.dp)
                )
            }
        }

        // Transaction history
        Text(
            "RIWAYAT TRANSAKSI",
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = SpaceMD)
        )
        Spacer(Modifier.height(SpaceXS))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = SpaceMD, vertical = SpaceXS),
            verticalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            items(TRANSACTIONS) { tx ->
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(containerColor = Canvas),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(SpaceSM),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(RadiusSM))
                                .background(if (tx.isIncome) GreenLight else ErrorLight)
                        ) {
                            Icon(tx.icon, contentDescription = null, tint = tx.accent, modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(SpaceSM))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(tx.title, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                            Text(tx.time, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }
                        Text(
                            tx.amount,
                            style = MaterialTheme.typography.titleSmall,
                            color = if (tx.isIncome) Green else TextPrimary
                        )
                    }
                }
            }
        }
    }
}
