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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

@Composable
fun MapPlaceholder(
    modifier: Modifier = Modifier,
    isHomeStyle: Boolean = false,
    showDriverIcon: Boolean = false,
    showRoute: Boolean = false
) {
    // Dark map background color
    val mapBgColor = Color(0xFF1E212A)
    val gridColor = Color(0xFF2C313E)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(RadiusMD))
            .background(mapBgColor),
        contentAlignment = Alignment.Center
    ) {
        // Grid lines to simulate map
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stepWidth = 40.dp.toPx()
            val stepHeight = 30.dp.toPx()
            
            // Draw grid lines
            var y = 0f
            while (y < size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                y += stepHeight
            }
            var x = 0f
            while (x < size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                x += stepWidth
            }

            // If isHomeStyle or showRoute is active (drawing the grid map with dots)
            if (isHomeStyle || showRoute) {
                // 1. Yellow dot top-left
                drawCircle(Color(0xFFFBD32C), radius = 6.dp.toPx(), center = Offset(size.width * 0.22f, size.height * 0.2f))
                
                // 2. Blue dot top-right
                drawCircle(Color(0xFF3B82F6), radius = 5.dp.toPx(), center = Offset(size.width * 0.65f, size.height * 0.22f))
                
                // 3. Green dot center-left
                drawCircle(Color(0xFF22C55E), radius = 5.dp.toPx(), center = Offset(size.width * 0.43f, size.height * 0.3f))
                
                // 4. Glowing Yellow dot center-right
                // Outer glow ring
                drawCircle(Color(0xFFFBD32C).copy(alpha = 0.3f), radius = 14.dp.toPx(), center = Offset(size.width * 0.51f, size.height * 0.25f))
                // Inner solid circle
                drawCircle(Color(0xFFFBD32C), radius = 6.dp.toPx(), center = Offset(size.width * 0.51f, size.height * 0.25f))
                
                // 5. Yellow dot bottom-left
                drawCircle(Color(0xFFFBD32C), radius = 5.dp.toPx(), center = Offset(size.width * 0.2f, size.height * 0.75f))
                
                // 6. Yellow dot bottom-right
                drawCircle(Color(0xFFFBD32C), radius = 5.dp.toPx(), center = Offset(size.width * 0.82f, size.height * 0.7f))

                if (showRoute) {
                    // Dashed yellow curve connecting glowing yellow dot and blue dot
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(size.width * 0.51f, size.height * 0.25f)
                        quadraticTo(
                            size.width * 0.58f, size.height * 0.18f,
                            size.width * 0.65f, size.height * 0.22f
                        )
                    }
                    val dash = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
                    drawPath(
                        path = path,
                        color = Color(0xFFFBD32C),
                        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3.dp.toPx(), pathEffect = dash)
                    )
                }
            } else {
                // Simulated roads for active screens
                val roadColor = Color(0xFF374151)
                drawLine(roadColor, Offset(0f, size.height * 0.4f), Offset(size.width, size.height * 0.35f), strokeWidth = 12f)
                drawLine(roadColor, Offset(size.width * 0.3f, 0f), Offset(size.width * 0.35f, size.height), strokeWidth = 12f)
                drawLine(roadColor, Offset(0f, size.height * 0.7f), Offset(size.width, size.height * 0.65f), strokeWidth = 8f)
                drawLine(roadColor, Offset(size.width * 0.65f, 0f), Offset(size.width * 0.7f, size.height), strokeWidth = 8f)
            }
        }

        // Home style bottom fade gradient overlay
        if (isHomeStyle) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Canvas.copy(alpha = 0.2f),
                                Canvas.copy(alpha = 0.8f),
                                Canvas
                            ),
                            startY = 0f
                        )
                    )
            )
        } else {
            // Location pin (only for active tracking/details)
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Blue)
                    .align(Alignment.Center)
            )

            // Driver icon (only for active tracking/details)
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

            // GPS button (only for active tracking/details)
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
}
