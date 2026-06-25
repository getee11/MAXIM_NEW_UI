package com.example.maxim_project.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SearchingScreen(onCancel: () -> Unit, onFound: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(4200)
        onFound()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "search")

    val scale1 by infiniteTransition.animateFloat(1f, 2.5f, infiniteRepeatable(tween(1500), RepeatMode.Restart), label = "s1")
    val alpha1 by infiniteTransition.animateFloat(0.4f, 0f, infiniteRepeatable(tween(1500), RepeatMode.Restart), label = "a1")
    val scale2 by infiniteTransition.animateFloat(1f, 2.5f, infiniteRepeatable(tween(1500, delayMillis = 500), RepeatMode.Restart), label = "s2")
    val alpha2 by infiniteTransition.animateFloat(0.4f, 0f, infiniteRepeatable(tween(1500, delayMillis = 500), RepeatMode.Restart), label = "a2")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Pulsing circles
        Box(contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .size(120.dp)
                    .scale(scale1)
                    .clip(CircleShape)
                    .background(MaximYellow.copy(alpha = alpha1))
            )
            Box(
                Modifier
                    .size(120.dp)
                    .scale(scale2)
                    .clip(CircleShape)
                    .background(MaximYellow.copy(alpha = alpha2))
            )
            Box(
                Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaximYellow),
                contentAlignment = Alignment.Center
            ) {
                Text("M", style = MaterialTheme.typography.displaySmall, color = TextPrimary)
            }
        }

        Spacer(Modifier.height(SpaceXL))

        Text(
            "MENCARI DRIVER",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(SpaceXS))
        Text(
            "Sedang mencari driver terdekat...\nEstimasi tunggu: 1-3 menit",
            style = MaterialTheme.typography.bodyMedium,
            color = TextBody,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(SpaceXL))

        SecondaryButton(
            text = "Batalkan Pesanan",
            onClick = onCancel,
            modifier = Modifier.padding(horizontal = SpaceXL),
            color = Error
        )
    }
}
