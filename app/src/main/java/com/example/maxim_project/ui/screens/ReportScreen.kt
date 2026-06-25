package com.example.maxim_project.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun ReportScreen(onBack: () -> Unit, onDone: () -> Unit, onCS: () -> Unit) {
    var isSubmitted by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    if (!isSubmitted) {
        // --- STATE 1: REPORT FORM ---
        val selectedViolations = remember { mutableStateListOf<Int>() }
        var description by remember { mutableStateOf("") }
        val violations = listOf(
            "Minta tarif lebih",
            "Perilaku tidak sopan",
            "Mengemudi ugal-ugalan",
            "Kendaraan tidak sesuai",
            "Membatalkan perjalanan",
            "Lainnya"
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Canvas)
        ) {
            MaximNavBar(
                title = "LAPORKAN DRIVER",
                titleColor = Error,
                backButtonTint = Error,
                onBack = onBack
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(SpaceMD),
                verticalArrangement = Arrangement.spacedBy(SpaceMD)
            ) {
                // Driver Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Hairline),
                    shape = RoundedCornerShape(RadiusMD)
                ) {
                    Row(
                        modifier = Modifier.padding(SpaceMD),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Cream-Yellow Circular Avatar
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(52.dp)
                                .background(Color(0xFFFFFBEB), CircleShape)
                                .border(1.5.dp, Color(0xFFFFD600), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(Modifier.width(SpaceMD))
                        
                        // Driver Info
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "BUDI SANTOSO",
                                fontSize = 16.sp,
                                fontFamily = DisplayFont,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Spacer(Modifier.height(2.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Toyota Avanza • B 1234 KLM",
                                    fontSize = 11.sp,
                                    fontFamily = MonoFont,
                                    color = TextMuted,
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                Spacer(Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFFFD600),
                                    modifier = Modifier.size(11.dp)
                                )
                                Spacer(Modifier.width(2.dp))
                                Text(
                                    text = "4.97",
                                    fontSize = 11.sp,
                                    fontFamily = MonoFont,
                                    color = TextMuted,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                        }
                        Spacer(Modifier.width(SpaceSM))

                        // Red Pill "DILAPORKAN" Badge
                        Box(
                            modifier = Modifier
                                .background(Error, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "DILAPORKAN",
                                fontSize = 9.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }

                // Checklist Section Title
                Text(
                    text = "JENIS PELANGGARAN",
                    fontSize = 12.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 1.sp
                )

                // 6 Checklist Items
                Column(verticalArrangement = Arrangement.spacedBy(SpaceXS)) {
                    violations.forEachIndexed { index, label ->
                        val isSelected = selectedViolations.contains(index)
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, if (isSelected) Error else Hairline),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (isSelected) {
                                        selectedViolations.remove(index)
                                    } else {
                                        selectedViolations.add(index)
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceSM),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextPrimary,
                                    modifier = Modifier.weight(1f)
                                )
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = {
                                        if (isSelected) {
                                            selectedViolations.remove(index)
                                        } else {
                                            selectedViolations.add(index)
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Error,
                                        uncheckedColor = Hairline,
                                        checkmarkColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }

                // Details Textfield Title
                Text(
                    text = "DETAIL KEJADIAN",
                    fontSize = 12.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    letterSpacing = 1.sp
                )

                // Description Field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = { Text("Ceritakan apa yang terjadi...", fontSize = 14.sp, color = TextMuted) },
                    shape = RoundedCornerShape(RadiusSM),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Error,
                        unfocusedBorderColor = Hairline,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Error
                    ),
                    minLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )

                // Emergency Help Card: "BUTUH BANTUAN SEGERA?"
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    border = BorderStroke(1.5.dp, Blue),
                    shape = RoundedCornerShape(RadiusMD)
                ) {
                    Column(modifier = Modifier.padding(SpaceMD)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Headset,
                                contentDescription = null,
                                tint = Blue,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(SpaceXS))
                            Text(
                                text = "BUTUH BANTUAN SEGERA?",
                                fontSize = 13.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = Blue,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(Modifier.height(SpaceXS))
                        Text(
                            text = "Layanan Customer Support kami aktif 24/7 untuk membantumu menyelesaikan masalah darurat.",
                            fontSize = 12.sp,
                            fontFamily = BodyFont,
                            color = TextBody,
                            lineHeight = 16.sp
                        )
                        Spacer(Modifier.height(SpaceMD))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(SpaceSM),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = onCS,
                                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                                shape = RoundedCornerShape(RadiusSM),
                                modifier = Modifier.weight(1f).height(44.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("CHAT CS", style = MaterialTheme.typography.labelMedium, color = Color.White)
                            }
                            OutlinedButton(
                                onClick = {
                                    Toast.makeText(context, "Menghubungi Customer Support...", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Blue),
                                border = BorderStroke(1.dp, Blue),
                                shape = RoundedCornerShape(RadiusSM),
                                modifier = Modifier.weight(1f).height(44.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp)
                            ) {
                                Text("TELEPON CS", style = MaterialTheme.typography.labelMedium, color = Blue)
                            }
                        }
                    }
                }
            }

            // Bottom Buttons
            Column(
                modifier = Modifier
                    .padding(SpaceMD)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val trimmedDesc = description.trim()
                val isDescriptionValid = trimmedDesc.length >= 10 &&
                        trimmedDesc.contains(" ") &&
                        trimmedDesc.filter { it.isLetter() }.toSet().size >= 4

                PrimaryButton(
                    text = "Kirim Laporan",
                    onClick = { isSubmitted = true },
                    enabled = selectedViolations.isNotEmpty() && isDescriptionValid,
                    color = Error,
                    textColor = Color.White
                )
                Spacer(Modifier.height(SpaceXS))
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(
                        text = "BATAL",
                        style = MaterialTheme.typography.labelLarge,
                        color = TextBody,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    } else {
        // --- STATE 2: REPORT SUBMITTED SUCCESS FEEDBACK ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Canvas)
                .padding(SpaceLG),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Centered Checkmark Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
                    .background(Blue, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(56.dp)
                )
            }
            Spacer(Modifier.height(SpaceLG))

            // Title and Subtext
            Text(
                text = "LAPORAN TERKIRIM",
                fontSize = 24.sp,
                fontFamily = DisplayFont,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(SpaceSM))
            Text(
                text = "Tim Maxim akan menindaklanjuti laporan kamu dalam 1x24 jam.",
                fontSize = 14.sp,
                fontFamily = BodyFont,
                color = TextBody,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = SpaceSM),
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(SpaceXL))

            // Action Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(SpaceSM),
                modifier = Modifier.fillMaxWidth()
            ) {
                // CHAT DENGAN CS
                Button(
                    onClick = onCS,
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    shape = RoundedCornerShape(RadiusSM),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("CHAT DENGAN CS", style = MaterialTheme.typography.labelLarge, color = Color.White)
                }

                // TELEPON CS
                OutlinedButton(
                    onClick = {
                        Toast.makeText(context, "Menghubungi Customer Support...", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Blue),
                    border = BorderStroke(1.5.dp, Blue),
                    shape = RoundedCornerShape(RadiusSM),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("TELEPON CS", style = MaterialTheme.typography.labelLarge, color = Blue)
                }

                // KEMBALI KE BERANDA (with custom golden/yellow outline border)
                OutlinedButton(
                    onClick = onDone,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                    border = BorderStroke(2.dp, Color(0xFFFFD600)),
                    shape = RoundedCornerShape(RadiusSM),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("KEMBALI KE BERANDA", style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                }
            }
        }
    }
}
