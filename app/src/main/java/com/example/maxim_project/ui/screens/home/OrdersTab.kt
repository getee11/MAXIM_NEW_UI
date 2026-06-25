package com.example.maxim_project.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.OrderCard
import com.example.maxim_project.ui.components.OrderData
import com.example.maxim_project.ui.theme.*

val SAMPLE_ORDERS = listOf(
    OrderData("24 Jun 2026, 14:30", "Jl. A. Yani Km 5", "Duta Mall", "Motor", "Rp 18.000", "Selesai", MaximYellow),
    OrderData("23 Jun 2026, 09:15", "Universitas Lambung Mangkurat", "Jl. Veteran", "Mobil", "Rp 35.000", "Selesai", MaximGold),
    OrderData("22 Jun 2026, 19:45", "Kohy Coffee", "Kos Jl. Pramuka", "Motor", "Rp 12.000", "Dibatalkan", Error),
    OrderData("21 Jun 2026, 12:00", "Jl. Gatot Subroto", "RS Ulin", "Kurir", "Rp 22.000", "Selesai", Blue),
    OrderData("20 Jun 2026, 08:30", "Kos Jl. Pramuka", "Kampus FKIP", "Motor", "Rp 15.000", "Selesai", MaximYellow),
)

@Composable
fun OrdersTab(onOrderClick: (OrderData) -> Unit) {
    var selectedFilter by remember { mutableIntStateOf(0) }
    val filters = listOf("Semua", "Aktif", "Selesai", "Dibatalkan")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        // Header
        Spacer(Modifier.height(StatusBarHeight + SpaceSM))
        Text(
            "PESANAN SAYA",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = SpaceMD)
        )
        Spacer(Modifier.height(SpaceMD))

        // Filter tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            horizontalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            filters.forEachIndexed { i, label ->
                val sel = i == selectedFilter
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(RadiusXS))
                        .background(if (sel) MaximYellow else Surface)
                        .clickable { selectedFilter = i }
                        .padding(horizontal = SpaceSM, vertical = SpaceXS)
                ) {
                    Text(
                        label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (sel) TextPrimary else TextMuted
                    )
                }
            }
        }

        Spacer(Modifier.height(SpaceMD))

        val filtered = when (selectedFilter) {
            1 -> SAMPLE_ORDERS.filter { it.status == "Aktif" }
            2 -> SAMPLE_ORDERS.filter { it.status == "Selesai" }
            3 -> SAMPLE_ORDERS.filter { it.status == "Dibatalkan" }
            else -> SAMPLE_ORDERS
        }

        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Belum ada pesanan", style = MaterialTheme.typography.bodyMedium, color = TextMuted)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = SpaceMD, vertical = SpaceXS),
                verticalArrangement = Arrangement.spacedBy(SpaceSM)
            ) {
                items(filtered) { order ->
                    OrderCard(order = order, onClick = { onOrderClick(order) })
                }
                item { Spacer(Modifier.height(BottomBarHeight + SpaceMD)) }
            }
        }
    }
}
