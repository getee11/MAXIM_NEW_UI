package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.MaximTextButton
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private val QUICK_REVIEWS = listOf("Ramah", "Tepat waktu", "Mengemudi aman", "Kendaraan bersih", "Perlu diperbaiki")

@Composable
fun RatingScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
    onReport: () -> Unit,
    onCS: () -> Unit
) {
    var rating by remember { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val selectedTags = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Rating", onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(SpaceLG),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Driver photo
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(YellowLight)
                    .border(2.dp, MaximYellow, CircleShape)
            ) {
                Text("AS", style = MaterialTheme.typography.headlineMedium, color = MaximDarkGold)
            }
            Spacer(Modifier.height(SpaceSM))
            Text("Ahmad Suryadi", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
            Text("Economy Bike • DA 1234 AB", style = MaterialTheme.typography.bodySmall, color = TextMuted)

            Spacer(Modifier.height(SpaceLG))

            // Star rating
            Row(horizontalArrangement = Arrangement.spacedBy(SpaceXS)) {
                repeat(5) { i ->
                    IconButton(onClick = { rating = i + 1 }) {
                        Icon(
                            if (i < rating) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = "Star ${i + 1}",
                            tint = if (i < rating) MaximGold else Hairline,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            if (rating > 0) {
                Text(
                    when (rating) {
                        1 -> "Sangat Buruk"
                        2 -> "Buruk"
                        3 -> "Cukup"
                        4 -> "Baik"
                        5 -> "Sangat Baik!"
                        else -> ""
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = if (rating >= 4) Green else if (rating >= 3) MaximGold else Error
                )
            }

            Spacer(Modifier.height(SpaceLG))

            // Quick review tags
            Text("ULASAN CEPAT", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(Modifier.height(SpaceXS))

            @OptIn(ExperimentalLayoutApi::class)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(SpaceXS),
                verticalArrangement = Arrangement.spacedBy(SpaceXS),
                modifier = Modifier.fillMaxWidth()
            ) {
                QUICK_REVIEWS.forEach { tag ->
                    val isSelected = tag in selectedTags
                    val isNegative = tag == "Perlu diperbaiki"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(RadiusBub))
                            .background(
                                when {
                                    isSelected && isNegative -> ErrorLight
                                    isSelected -> GreenLight
                                    else -> Surface
                                }
                            )
                            .border(
                                1.dp,
                                when {
                                    isSelected && isNegative -> Error
                                    isSelected -> Green
                                    else -> Hairline
                                },
                                RoundedCornerShape(RadiusBub)
                            )
                            .clickable {
                                if (isSelected) selectedTags.remove(tag) else selectedTags.add(tag)
                            }
                            .padding(horizontal = SpaceMD, vertical = SpaceXS)
                    ) {
                        Text(
                            tag,
                            style = MaterialTheme.typography.bodySmall,
                            color = when {
                                isSelected && isNegative -> Error
                                isSelected -> Green
                                else -> TextBody
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(SpaceMD))

            // Comment
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                placeholder = { Text("Tulis komentar (opsional)") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(SpaceSM))
            Row {
                MaximTextButton("Laporkan Masalah", onClick = onReport, color = Error)
                Spacer(Modifier.width(SpaceMD))
                MaximTextButton("Hubungi CS", onClick = onCS, color = Blue)
            }
        }

        // Bottom buttons
        Column(modifier = Modifier.padding(SpaceMD)) {
            PrimaryButton("Kirim Rating", onClick = onDone, enabled = rating > 0)
            Spacer(Modifier.height(SpaceXS))
            MaximTextButton(
                "Lewati",
                onClick = onDone,
                color = TextMuted,
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    }
}
