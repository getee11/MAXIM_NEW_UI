package com.example.maxim_project.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

@Composable
fun MapPlaceholder(
    modifier: Modifier = Modifier,
    showDriverIcon: Boolean = false,
    showRoute: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(RadiusMD))
            .background(SurfaceElev),
        contentAlignment = Alignment.Center
    ) {
        // Grid lines to simulate map
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridColor = Hairline.copy(alpha = 0.4f)
            val step = 40.dp.toPx()
            // Horizontal lines
            var y = 0f
            while (y < size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 0.5f)
                y += step
            }
            // Vertical lines
            var x = 0f
            while (x < size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 0.5f)
                x += step
            }

            // Simulated roads
            val roadColor = Hairline.copy(alpha = 0.6f)
            drawLine(roadColor, Offset(0f, size.height * 0.4f), Offset(size.width, size.height * 0.35f), strokeWidth = 6f)
            drawLine(roadColor, Offset(size.width * 0.3f, 0f), Offset(size.width * 0.35f, size.height), strokeWidth = 6f)
            drawLine(roadColor, Offset(0f, size.height * 0.7f), Offset(size.width, size.height * 0.65f), strokeWidth = 4f)
            drawLine(roadColor, Offset(size.width * 0.65f, 0f), Offset(size.width * 0.7f, size.height), strokeWidth = 4f)

            if (showRoute) {
                val routeColor = Blue
                val dash = PathEffect.dashPathEffect(floatArrayOf(12f, 8f))
                drawLine(
                    routeColor,
                    Offset(size.width * 0.3f, size.height * 0.7f),
                    Offset(size.width * 0.65f, size.height * 0.35f),
                    strokeWidth = 4f,
                    pathEffect = dash
                )
            }
        }

        // Location pin
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(Blue)
                .align(Alignment.Center)
        )

        // Driver icon
        if (showDriverIcon) {
            Box(
                modifier = Modifier
                    .offset(x = (-40).dp, y = (-30).dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaximYellow)
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Navigation,
                    contentDescription = "Driver",
                    tint = TextPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // GPS button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SpaceMD)
                .size(40.dp)
                .clip(CircleShape)
                .background(Canvas),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.MyLocation,
                contentDescription = "GPS",
                tint = Blue,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
