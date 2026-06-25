package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.theme.*
import com.example.maxim_project.data.InMemoryDatabase

private data class ChatMsg(val text: String, val isSent: Boolean, val time: String)

@Composable
fun DriverChatScreen(onBack: () -> Unit) {
    val driverName = InMemoryDatabase.currentDriver.namaDriver

    var input by remember { mutableStateOf("") }
    val messages = remember {
        mutableStateListOf(
            ChatMsg("Halo, saya sudah di perjalanan ke lokasi jemput Anda", false, "14:32"),
            ChatMsg("Baik pak, saya tunggu di depan gerbang ya", true, "14:33"),
            ChatMsg("Siap, estimasi 3 menit lagi sampai", false, "14:33"),
            ChatMsg("Oke terima kasih 🙏", true, "14:34"),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Chat • ${driverName}", onBack = onBack)

        // Chat messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = SpaceMD),
            contentPadding = PaddingValues(vertical = SpaceSM),
            verticalArrangement = Arrangement.spacedBy(SpaceXS),
            reverseLayout = false
        ) {
            items(messages) { msg ->
                ChatBubble(msg)
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
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(SpaceXS))
            IconButton(
                onClick = {
                    if (input.isNotBlank()) {
                        messages.add(ChatMsg(input.trim(), true, "14:35"))
                        input = ""
                    }
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(RadiusSM))
                        .background(if (input.isNotBlank()) MaximYellow else Surface)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = if (input.isNotBlank()) TextPrimary else TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatBubble(msg: ChatMsg) {
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
                .background(if (msg.isSent) MaximYellow else Surface)
                .padding(horizontal = SpaceMD, vertical = SpaceSM)
        ) {
            Column {
                Text(
                    msg.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (msg.isSent) TextPrimary else TextPrimary
                )
                Spacer(Modifier.height(SpaceXXS))
                Text(
                    msg.time,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (msg.isSent) MaximDarkGold else TextMuted
                )
            }
        }
    }
}
