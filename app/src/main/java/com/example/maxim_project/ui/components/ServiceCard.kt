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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.theme.*

@Composable
fun ServiceCard(
    icon: ImageVector,
    label: String,
    subtitle: String,
    priceLabel: String,
    accentColor: Color,
    priceColor: Color,
    tintBg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(RadiusMD)
    val iconContainerShape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .height(165.dp)
            .fillMaxWidth()
            .clip(cardShape)
            .background(Color.White)
            .border(1.dp, Hairline.copy(alpha = 0.5f), cardShape)
            .clickable(onClick = onClick)
    ) {
        // Colored indicator line on the left side of the card
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(accentColor)
                .align(Alignment.CenterStart)
        )

        // Main content column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 16.dp, end = 12.dp, bottom = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon Container
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(iconContainerShape)
                    .background(tintBg)
            ) {
                Icon(
                    icon,
                    contentDescription = label,
                    tint = priceColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Labels and details
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // Title (e.g. BIKE)
                Text(
                    text = label.uppercase(),
                    fontSize = 18.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )

                // Subtitle (e.g. OJEK CEPAT)
                Text(
                    text = subtitle.uppercase(),
                    fontSize = 10.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Normal,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Price (e.g. MULAI RP 8.000)
                Text(
                    text = priceLabel.uppercase(),
                    fontSize = 9.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = priceColor,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}
