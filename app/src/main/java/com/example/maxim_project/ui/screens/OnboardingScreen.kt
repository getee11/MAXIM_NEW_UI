package com.example.maxim_project.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximTextButton
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*

private data class Slide(
    val icon: ImageVector,
    val svc: String,
    val title: String,
    val desc: String,
    val accent: Color,
    val tintBg: Color
)

private val SLIDES = listOf(
    Slide(Icons.Default.TwoWheeler, "MOTOR", "SAMPAI\nLEBIH CEPAT", "Ojek motor untuk perjalanan cepat di kota. Lewati kemacetan, sampai tepat waktu.", MaximYellow, YellowLight),
    Slide(Icons.Default.DirectionsCar, "MOBIL", "NYAMAN\nDI PERJALANAN", "Taksi mobil premium dengan driver profesional di ujung jari Anda.", MaximGold, GoldLight),
    Slide(Icons.Default.LocalShipping, "KURIR", "KIRIM\nAPA SAJA", "Pengiriman same-day untuk dokumen dan paket ke seluruh kota.", Blue, BlueLight),
    Slide(Icons.Default.Restaurant, "MAKANAN", "MAKAN TANPA\nBERGERAK", "Restoran favorit diantar ke pintu rumah dalam hitungan menit.", Terracotta, TerracottaLight),
)

@Composable
fun OnboardingScreen(onDone: () -> Unit) {
    var idx by remember { mutableIntStateOf(0) }
    val slide = SLIDES[idx]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        // Skip button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = StatusBarHeight + SpaceXS, end = SpaceMD),
            horizontalArrangement = Arrangement.End
        ) {
            MaximTextButton(text = "Lewati", onClick = onDone, color = TextMuted)
        }

        // Slide content
        AnimatedContent(
            targetState = idx,
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith
                slideOutHorizontally { -it } + fadeOut()
            },
            modifier = Modifier.weight(1f),
            label = "slide"
        ) { currentIdx ->
            val s = SLIDES[currentIdx]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Icon box
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(116.dp)
                        .clip(RoundedCornerShape(RadiusMD))
                        .background(s.tintBg)
                        .border(2.dp, s.accent, RoundedCornerShape(RadiusMD))
                ) {
                    Icon(s.icon, contentDescription = null, tint = s.accent, modifier = Modifier.size(48.dp))
                }
                Spacer(Modifier.height(44.dp))
                Text(
                    s.svc,
                    style = MaterialTheme.typography.labelMedium,
                    color = if (s.accent == MaximYellow) MaximDarkGold else s.accent
                )
                Spacer(Modifier.height(SpaceSM))
                Text(
                    s.title,
                    style = MaterialTheme.typography.displayMedium,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(SpaceMD))
                Text(
                    s.desc,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextBody,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Bottom: dots + buttons
        Column(modifier = Modifier.padding(horizontal = 40.dp, vertical = 52.dp)) {
            // Pagination dots
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                SLIDES.forEachIndexed { i, s ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(4.dp)
                            .width(if (i == idx) 28.dp else 6.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(if (i == idx) s.accent else Hairline)
                            .clickable { idx = i }
                    )
                }
            }
            Spacer(Modifier.height(28.dp))

            if (idx < SLIDES.size - 1) {
                PrimaryButton(
                    text = "Selanjutnya",
                    onClick = { idx++ },
                    color = slide.accent,
                    textColor = if (slide.accent == MaximYellow) TextPrimary else Canvas
                )
            } else {
                PrimaryButton(
                    text = "Mulai",
                    onClick = onDone,
                    color = slide.accent,
                    textColor = Canvas
                )
            }
        }
    }
}
