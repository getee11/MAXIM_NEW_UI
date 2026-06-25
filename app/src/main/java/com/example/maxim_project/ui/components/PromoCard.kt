package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

data class PromoData(
    val title: String,
    val desc: String,
    val code: String,
    val expires: String,
    val accent: Color,
    val tintBg: Color
)

@Composable
fun PromoCard(
    promo: PromoData,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(RadiusMD)
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = Canvas),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            // Banner strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(promo.accent)
            )
            Column(modifier = Modifier.padding(SpaceMD)) {
                Text(promo.title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                Spacer(Modifier.height(SpaceXXS))
                Text(promo.desc, style = MaterialTheme.typography.bodySmall, color = TextBody)
                Spacer(Modifier.height(SpaceSM))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Promo code chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(RadiusXS))
                            .background(promo.tintBg)
                            .clickable(onClick = onCopy)
                            .padding(horizontal = SpaceSM, vertical = SpaceXS),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            promo.code,
                            style = MaterialTheme.typography.labelMedium,
                            color = promo.accent
                        )
                        Spacer(Modifier.width(SpaceXS))
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "Copy",
                            tint = promo.accent,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        "s.d. ${promo.expires}",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted
                    )
                }
            }
        }
    }
}
