package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.MaximTextButton
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private data class ReportCategory(val icon: ImageVector, val label: String, val accent: Color)

private val CATEGORIES = listOf(
    ReportCategory(Icons.Default.Warning, "Keselamatan", Error),
    ReportCategory(Icons.Default.MoneyOff, "Masalah Pembayaran", MaximGold),
    ReportCategory(Icons.Default.PersonOff, "Perilaku Driver", Blue),
    ReportCategory(Icons.Default.LocalShipping, "Barang Hilang", Purple),
    ReportCategory(Icons.Default.Route, "Rute Salah", Terracotta),
    ReportCategory(Icons.Default.BugReport, "Masalah Teknis", TextMuted),
)

@Composable
fun ReportScreen(onBack: () -> Unit, onDone: () -> Unit, onCS: () -> Unit) {
    var selectedCategory by remember { mutableIntStateOf(-1) }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Laporan", onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(SpaceMD)
        ) {
            Text("PILIH KATEGORI MASALAH", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(Modifier.height(SpaceSM))

            CATEGORIES.forEachIndexed { i, cat ->
                val sel = i == selectedCategory
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(RadiusSM))
                        .then(
                            if (sel) Modifier
                                .background(cat.accent.copy(alpha = 0.08f))
                                .border(1.dp, cat.accent, RoundedCornerShape(RadiusSM))
                            else Modifier.border(1.dp, Hairline, RoundedCornerShape(RadiusSM))
                        )
                        .clickable { selectedCategory = i }
                        .padding(SpaceMD),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(cat.icon, contentDescription = null, tint = cat.accent, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(SpaceSM))
                    Text(cat.label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    Spacer(Modifier.weight(1f))
                    RadioButton(
                        selected = sel,
                        onClick = { selectedCategory = i },
                        colors = RadioButtonDefaults.colors(selectedColor = cat.accent)
                    )
                }
                Spacer(Modifier.height(SpaceXS))
            }

            Spacer(Modifier.height(SpaceMD))

            Text("DESKRIPSI", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(Modifier.height(SpaceXS))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Jelaskan masalah yang Anda alami...") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (selectedCategory >= 0) CATEGORIES[selectedCategory].accent else MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(SpaceMD))
            MaximTextButton("Langsung hubungi Customer Support →", onClick = onCS, color = Blue)
        }

        Column(modifier = Modifier.padding(SpaceMD)) {
            PrimaryButton(
                text = "Kirim Laporan",
                onClick = onDone,
                enabled = selectedCategory >= 0 && description.isNotBlank(),
                color = if (selectedCategory >= 0) CATEGORIES[selectedCategory].accent else MaximYellow,
                textColor = Canvas
            )
        }
    }
}
