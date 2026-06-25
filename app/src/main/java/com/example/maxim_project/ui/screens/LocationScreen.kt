package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private val SUGGESTIONS = listOf(
    "Duta Mall, Jl. A. Yani Km 2",
    "Universitas Lambung Mangkurat",
    "RS Ulin Banjarmasin",
    "Bandara Syamsudin Noor",
    "Pasar Baru Banjarmasin",
    "Masjid Raya Sabilal Muhtadin",
)

private val RECENT = listOf(
    "Jl. Sudirman No. 12, Jakarta Pusat",
    "Mall Taman Anggrek, Jakarta Barat",
    "Kemang Raya No. 45, Jakarta Selatan",
    "Grand Indonesia, Jakarta Pusat",
)

enum class FavoriteType {
    HOME, OFFICE
}

@Composable
fun LocationScreen(onBack: () -> Unit, onNext: () -> Unit) {
    var pickupText by rememberSaveable { mutableStateOf("Lokasi saat ini") }
    var destText by rememberSaveable { mutableStateOf("") }
    var showSuggestions by remember { mutableStateOf(false) }

    // State for Favorite Addresses
    var homeAddress by rememberSaveable { mutableStateOf("") }
    var officeAddress by rememberSaveable { mutableStateOf("") }

    // Dialog state
    var showSaveDialog by remember { mutableStateOf(false) }
    var tempAddressText by remember { mutableStateOf("") }
    var tempAddressType by remember { mutableStateOf(FavoriteType.HOME) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "PILIH LOKASI", onBack = onBack)

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = SpaceLG)
        ) {
            // Pickup & Destination Inputs (Unified Card Layout)
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceMD)
                        .clip(RoundedCornerShape(RadiusMD))
                        .background(Color.White)
                        .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
                        .padding(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // DARI (Pickup)
                        Column {
                            Text(
                                "DARI",
                                fontSize = 10.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted,
                                letterSpacing = 1.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Green Dot
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Green)
                                )
                                Spacer(Modifier.width(12.dp))
                                BasicTextField(
                                    value = pickupText,
                                    onValueChange = { pickupText = it },
                                    textStyle = TextStyle(
                                        color = TextPrimary,
                                        fontSize = 14.sp,
                                        fontFamily = BodyFont
                                    ),
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        HorizontalDivider(color = Hairline.copy(alpha = 0.4f), thickness = 1.dp)

                        // KE (Destination)
                        Column {
                            Text(
                                "KE",
                                fontSize = 10.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted,
                                letterSpacing = 1.sp
                            )
                            Spacer(Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // Red Square
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Error)
                                )
                                Spacer(Modifier.width(12.dp))
                                
                                // Cursor text field for destination
                                Box(modifier = Modifier.weight(1f)) {
                                    if (destText.isEmpty()) {
                                        Text(
                                            "Masukkan tujuan...",
                                            color = TextMuted,
                                            fontSize = 14.sp,
                                            fontFamily = BodyFont
                                        )
                                    }
                                    BasicTextField(
                                        value = destText,
                                        onValueChange = {
                                            destText = it
                                            showSuggestions = it.isNotEmpty()
                                        },
                                        textStyle = TextStyle(
                                            color = TextPrimary,
                                            fontSize = 14.sp,
                                            fontFamily = BodyFont
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Search
                                        ),
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Suggestions List
            if (showSuggestions) {
                val filteredSuggestions = SUGGESTIONS.filter { it.contains(destText, ignoreCase = true) }
                if (filteredSuggestions.isNotEmpty()) {
                    item {
                        Text(
                            "SARAN LOKASI",
                            fontSize = 11.sp,
                            fontFamily = MonoFont,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)
                        )
                    }
                    items(filteredSuggestions) { loc ->
                        LocationSuggestionItem(loc, Icons.Default.LocationOn) {
                            destText = loc
                            showSuggestions = false
                        }
                    }
                }
            } else {
                // Section: LOKASI FAVORIT (RUMAH & KANTOR)
                item {
                    Text(
                        "LOKASI FAVORIT",
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)
                    )
                }

                // RUMAH Row Card
                item {
                    FavoriteLocationRow(
                        title = "RUMAH",
                        address = homeAddress,
                        icon = Icons.Outlined.Home,
                        onCardClick = {
                            if (homeAddress.isNotEmpty()) {
                                destText = homeAddress
                            }
                        },
                        onAddClick = {
                            tempAddressText = homeAddress
                            tempAddressType = FavoriteType.HOME
                            showSaveDialog = true
                        }
                    )
                }

                // KANTOR Row Card
                item {
                    FavoriteLocationRow(
                        title = "KANTOR",
                        address = officeAddress,
                        icon = Icons.Outlined.Assignment,
                        onCardClick = {
                            if (officeAddress.isNotEmpty()) {
                                destText = officeAddress
                            }
                        },
                        onAddClick = {
                            tempAddressText = officeAddress
                            tempAddressType = FavoriteType.OFFICE
                            showSaveDialog = true
                        }
                    )
                    Spacer(Modifier.height(16.dp))
                }

                // Section: LOKASI TERAKHIR
                item {
                    Text(
                        "LOKASI TERAKHIR",
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        modifier = Modifier.padding(horizontal = SpaceMD, vertical = SpaceXS)
                    )
                }

                items(RECENT) { loc ->
                    LocationRecentCard(loc) {
                        destText = loc
                        showSuggestions = false
                    }
                }
            }
        }

        // Confirm button
        Box(modifier = Modifier.padding(SpaceMD)) {
            PrimaryButton(
                text = "KONFIRMASI LOKASI",
                onClick = onNext,
                color = Color(0xFFFFE600), // Solid Yellow
                textColor = TextPrimary,
                enabled = destText.isNotEmpty()
            )
        }
    }

    // SIMPAN LOKASI pop-up Dialog
    if (showSaveDialog) {
        Dialog(onDismissRequest = { showSaveDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .border(1.dp, Hairline.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "SIMPAN LOKASI",
                        fontSize = 18.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Atur lokasi favorit untuk akses cepat",
                        fontSize = 13.sp,
                        fontFamily = BodyFont,
                        color = TextMuted
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Input Address field
                    OutlinedTextField(
                        value = tempAddressText,
                        onValueChange = { tempAddressText = it },
                        placeholder = { Text("Masukkan alamat lengkap...", fontFamily = BodyFont, fontSize = 14.sp) },
                        shape = RoundedCornerShape(RadiusSM),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFFD600),
                            unfocusedBorderColor = Hairline,
                            cursorColor = MaximDarkGold
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "PILIH TIPE",
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Selection buttons Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // RUMAH selection card
                        val homeSelected = tempAddressType == FavoriteType.HOME
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (homeSelected) YellowLight else Color.White)
                                .border(
                                    width = if (homeSelected) 1.5.dp else 1.dp,
                                    color = if (homeSelected) Color(0xFFFFD600) else Hairline,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { tempAddressType = FavoriteType.HOME }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.Home,
                                    contentDescription = null,
                                    tint = if (homeSelected) MaximDarkGold else TextMuted,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "RUMAH",
                                    fontSize = 11.sp,
                                    fontFamily = DisplayFont,
                                    fontWeight = FontWeight.Bold,
                                    color = if (homeSelected) MaximDarkGold else TextMuted
                                )
                            }
                        }

                        // KANTOR selection card
                        val officeSelected = tempAddressType == FavoriteType.OFFICE
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (officeSelected) YellowLight else Color.White)
                                .border(
                                    width = if (officeSelected) 1.5.dp else 1.dp,
                                    color = if (officeSelected) Color(0xFFFFD600) else Hairline,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { tempAddressType = FavoriteType.OFFICE }
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Outlined.Assignment,
                                    contentDescription = null,
                                    tint = if (officeSelected) MaximDarkGold else TextMuted,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "KANTOR",
                                    fontSize = 11.sp,
                                    fontFamily = DisplayFont,
                                    fontWeight = FontWeight.Bold,
                                    color = if (officeSelected) MaximDarkGold else TextMuted
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // BATAL & SIMPAN Action Buttons Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // BATAL Button
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color.White)
                                .border(1.dp, Color(0xFFFFD600), RoundedCornerShape(22.dp))
                                .clickable { showSaveDialog = false }
                        ) {
                            Text(
                                "BATAL",
                                fontSize = 13.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = MaximDarkGold,
                                letterSpacing = 1.sp
                            )
                        }

                        // SIMPAN Button
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(Color(0xFFFFD600))
                                .clickable {
                                    if (tempAddressType == FavoriteType.HOME) {
                                        homeAddress = tempAddressText
                                    } else {
                                        officeAddress = tempAddressText
                                    }
                                    showSaveDialog = false
                                }
                        ) {
                            Text(
                                "SIMPAN",
                                fontSize = 13.sp,
                                fontFamily = MonoFont,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteLocationRow(
    title: String,
    address: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceMD, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Address Card
        Box(
            modifier = Modifier
                .weight(1f)
                .height(64.dp)
                .clip(RoundedCornerShape(RadiusMD))
                .background(Color.White)
                .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
                .clickable(onClick = onCardClick)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Icon wrapper Box
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Surface)
                ) {
                    Icon(
                        icon,
                        contentDescription = title,
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = address.ifEmpty { "Belum diatur" },
                        fontSize = 11.sp,
                        fontFamily = BodyFont,
                        color = if (address.isEmpty()) TextMuted else TextBody,
                        maxLines = 1
                    )
                }
            }
        }

        // Plus (+) button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(RadiusMD))
                .background(Color.White)
                .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
                .clickable(onClick = onAddClick)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add address",
                tint = TextBody,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun LocationRecentCard(address: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(RadiusMD),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = SpaceMD, vertical = 6.dp)
            .border(1.dp, Hairline.copy(alpha = 0.4f), RoundedCornerShape(RadiusMD))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Outlined.AccessTime,
                contentDescription = "History",
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = address,
                fontSize = 13.sp,
                fontFamily = BodyFont,
                color = TextBody
            )
        }
    }
}

@Composable
private fun LocationSuggestionItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = SpaceMD, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = TextMuted, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(16.dp))
        Text(
            text,
            fontSize = 14.sp,
            fontFamily = BodyFont,
            color = TextPrimary
        )
    }
}
