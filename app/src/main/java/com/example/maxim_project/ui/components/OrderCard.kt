package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

data class OrderData(
    val date: String,
    val from: String,
    val to: String,
    val type: String,
    val price: String,
    val status: String,
    val accent: Color,
    val driver: String = "Ahmad S.",
    val vehicle: String = "Honda Vario 150",
    val duration: String = "23 min",
    val distance: String = "8.4 km",
    val rating: Int = 5
)

@Composable
fun OrderCard(
    order: OrderData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(RadiusMD)
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Canvas),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(SpaceMD)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(order.date, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(RadiusXS))
                        .background(
                            when (order.status) {
                                "Selesai" -> GreenLight
                                "Dibatalkan" -> ErrorLight
                                else -> YellowLight
                            }
                        )
                        .padding(horizontal = SpaceXS, vertical = SpaceXXS)
                ) {
                    Text(
                        order.status,
                        style = MaterialTheme.typography.labelSmall,
                        color = when (order.status) {
                            "Selesai" -> Green
                            "Dibatalkan" -> Error
                            else -> MaximDarkGold
                        }
                    )
                }
            }
            Spacer(Modifier.height(SpaceSM))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Green)
                        )
                        Spacer(Modifier.width(SpaceXS))
                        Text(order.from, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, maxLines = 1)
                    }
                    Box(
                        Modifier
                            .padding(start = 3.dp)
                            .width(2.dp)
                            .height(16.dp)
                            .background(Hairline)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Error)
                        )
                        Spacer(Modifier.width(SpaceXS))
                        Text(order.to, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, maxLines = 1)
                    }
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
            }
            Spacer(Modifier.height(SpaceSM))
            HorizontalDivider(color = Hairline.copy(alpha = 0.5f))
            Spacer(Modifier.height(SpaceSM))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(order.type, style = MaterialTheme.typography.labelSmall, color = order.accent)
                Text(order.price, style = MaterialTheme.typography.titleSmall, color = TextPrimary)
            }
        }
    }
}
