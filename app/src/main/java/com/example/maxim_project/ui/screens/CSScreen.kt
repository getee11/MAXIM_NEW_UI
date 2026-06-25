package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.theme.*

private data class CSMsg(val text: String, val isSent: Boolean, val time: String)

private val QUICK_REPLIES = listOf(
    "Masalah pembayaran",
    "Driver tidak datang",
    "Barang rusak/hilang",
    "Pertanyaan promo",
    "Lainnya"
)

@Composable
fun CSScreen(onBack: () -> Unit) {
    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            CSMsg("Halo! 👋 Selamat datang di Maxim Customer Support. Ada yang bisa kami bantu?", false, "14:40"),
        )
    }
    var showQuickReplies by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Customer Support", onBack = onBack)

        // Chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = SpaceMD),
            contentPadding = PaddingValues(vertical = SpaceSM),
            verticalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            items(messages) { msg ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (msg.isSent) Alignment.End else Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = RadiusBub,
                                    topEnd = RadiusBub,
                                    bottomStart = if (msg.isSent) RadiusBub else SpaceXXS,
                                    bottomEnd = if (msg.isSent) SpaceXXS else RadiusBub
                                )
                            )
                            .background(if (msg.isSent) Blue else Surface)
                            .padding(horizontal = SpaceMD, vertical = SpaceSM)
                    ) {
                        Column {
                            Text(
                                msg.text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (msg.isSent) Canvas else TextPrimary
                            )
                            Spacer(Modifier.height(SpaceXXS))
                            Text(
                                msg.time,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (msg.isSent) Canvas.copy(alpha = 0.7f) else TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Quick replies
        if (showQuickReplies) {
            Column(modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)) {
                Text("BALASAN CEPAT", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                Spacer(Modifier.height(SpaceXS))
                @OptIn(ExperimentalLayoutApi::class)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(SpaceXS),
                    verticalArrangement = Arrangement.spacedBy(SpaceXS)
                ) {
                    QUICK_REPLIES.forEach { reply ->
                        SuggestionChip(
                            onClick = {
                                messages.add(CSMsg(reply, true, "14:41"))
                                messages.add(CSMsg("Baik, kami akan membantu terkait \"$reply\". Mohon tunggu sebentar ya.", false, "14:41"))
                                showQuickReplies = false
                            },
                            label = { Text(reply, style = MaterialTheme.typography.bodySmall) },
                            shape = RoundedCornerShape(RadiusBub),
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = BlueLight,
                                labelColor = Blue
                            )
                        )
                    }
                }
            }
        }

        // Input bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Canvas)
                .padding(SpaceSM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                placeholder = { Text("Ketik pesan...") },
                shape = RoundedCornerShape(RadiusBub),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue,
                    unfocusedBorderColor = Hairline,
                    cursorColor = Blue
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(SpaceXS))
            IconButton(
                onClick = {
                    if (input.isNotBlank()) {
                        messages.add(CSMsg(input.trim(), true, "14:42"))
                        input = ""
                        showQuickReplies = false
                    }
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(RadiusSM))
                        .background(if (input.isNotBlank()) Blue else Surface)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (input.isNotBlank()) Canvas else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
