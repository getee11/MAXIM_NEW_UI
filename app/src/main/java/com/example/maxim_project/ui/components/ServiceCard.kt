package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

@Composable
fun ServiceCard(
    icon: ImageVector,
    label: String,
    accent: Color,
    tintBg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(RadiusMD)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick)
            .padding(SpaceXS)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp)
                .clip(shape)
                .background(tintBg)
                .border(1.dp, accent.copy(alpha = 0.3f), shape)
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = accent,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.height(SpaceXS))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextBody
        )
    }
}
