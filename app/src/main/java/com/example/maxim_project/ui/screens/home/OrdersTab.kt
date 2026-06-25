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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maxim_project.data.viewmodel.ReportViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.OrderCard
import com.example.maxim_project.ui.components.OrderData
import com.example.maxim_project.ui.theme.*

@Composable
fun OrdersTab(
    reportViewModel: ReportViewModel = viewModel(),
    onOrderClick: (OrderData) -> Unit
) {
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

        val userTrips by reportViewModel.userTrips.collectAsStateWithLifecycle()
        
        val allOrders = userTrips.map { trip ->
            val parts = trip.rute.split("→")
            val fromLoc = parts.getOrNull(0)?.trim() ?: trip.rute
            val toLoc = parts.getOrNull(1)?.trim() ?: "Tujuan"
            
            OrderData(
                date = trip.tanggal,
                from = fromLoc,
                to = toLoc,
                type = "BIKE", // Default since we don't store it in Trip yet, or use "Maxim Bike"
                price = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("id", "ID")).format(trip.tarif).replace("Rp", "Rp "),
                status = if (trip.status == "COMPLETED") "Selesai" else "Aktif",
                accent = Color(0xFF6C5A25) // Default accent
            )
        }

        val filtered = when (selectedFilter) {
            1 -> allOrders.filter { it.status == "Aktif" }
            2 -> allOrders.filter { it.status == "Selesai" }
            3 -> allOrders.filter { it.status == "Dibatalkan" }
            else -> allOrders
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
