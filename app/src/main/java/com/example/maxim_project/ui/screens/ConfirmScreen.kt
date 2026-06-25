package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.maxim_project.data.viewmodel.ReportViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border

@Composable
fun ConfirmScreen(
    reportViewModel: ReportViewModel = viewModel(),
    initialPrice: String = "Rp 18.000",
    onBack: () -> Unit,
    onConfirm: () -> Unit
) {
    val context = LocalContext.current
    var promoCode by rememberSaveable { mutableStateOf("") }
    var isPromoApplied by rememberSaveable { mutableStateOf(false) }
    var hasHeavyLuggage by rememberSaveable { mutableStateOf(false) }
    var hasPet by rememberSaveable { mutableStateOf(false) }
    var hasChild by rememberSaveable { mutableStateOf(false) }
    var driverNote by rememberSaveable { mutableStateOf("") }
    var selectedPaymentMethod by rememberSaveable { mutableStateOf("DOMPET") } // DOMPET or TUNAI
    
    val currentUser by reportViewModel.currentUser.collectAsStateWithLifecycle()
    val walletBalance = currentUser?.saldo ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "KONFIRMASI ORDER", onBack = onBack)

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(SpaceMD),
            verticalArrangement = Arrangement.spacedBy(SpaceMD)
        ) {
            // Trip summary / Total
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("TOTAL", fontSize = 11.sp, fontFamily = MonoFont, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = if (isPromoApplied) {
                            // Basic logic to subtract 5000 if promo applied, assuming format "Rp 18.000"
                            val num = initialPrice.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 18000
                            "Rp ${java.text.DecimalFormat("#,###").format(maxOf(0, num - 5000)).replace(',', '.')}"
                        } else {
                            initialPrice
                        },
                        fontSize = 28.sp,
                        fontFamily = DisplayFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    if (isPromoApplied) {
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Promo diterapkan: Potongan Rp 5.000",
                            fontSize = 11.sp,
                            fontFamily = BodyFont,
                            color = Green,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Metode Pembayaran section
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("METODE PEMBAYARAN", fontSize = 11.sp, fontFamily = MonoFont, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(Modifier.height(12.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val isDompet = selectedPaymentMethod == "DOMPET"
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isDompet) Color(0xFFFFE600) else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (isDompet) Color(0xFFFFE600) else Hairline.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { selectedPaymentMethod = "DOMPET" }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AccountBalanceWallet,
                                    contentDescription = null,
                                    tint = TextPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "DOMPET",
                                        fontSize = 11.sp,
                                        fontFamily = MonoFont,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "Rp " + java.text.DecimalFormat("#,###").format(walletBalance).replace(',', '.'),
                                        fontSize = 9.sp,
                                        fontFamily = BodyFont,
                                        color = TextPrimary.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }

                        val isTunai = selectedPaymentMethod == "TUNAI"
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isTunai) Color(0xFFFFE600) else Color.White)
                                .border(
                                    width = 1.dp,
                                    color = if (isTunai) Color(0xFFFFE600) else Hairline.copy(alpha = 0.5f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { selectedPaymentMethod = "TUNAI" }
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Money,
                                    contentDescription = null,
                                    tint = if (isTunai) TextPrimary else TextBody,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "TUNAI",
                                    fontSize = 11.sp,
                                    fontFamily = MonoFont,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTunai) TextPrimary else TextBody
                                )
                            }
                        }
                    }
                }
            }

            // Kode Promo section
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("KODE PROMO", fontSize = 11.sp, fontFamily = MonoFont, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = promoCode,
                            onValueChange = { promoCode = it },
                            placeholder = { Text("Masukkan kode promo.", fontSize = 13.sp, color = TextMuted) },
                            shape = RoundedCornerShape(RadiusSM),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaximDarkGold,
                                unfocusedBorderColor = Hairline,
                                cursorColor = MaximDarkGold
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Button(
                            onClick = {
                                if (promoCode.isNotBlank()) {
                                    isPromoApplied = true
                                    android.widget.Toast.makeText(context, "Promo Berhasil Diterapkan!", android.widget.Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFFFDE7),
                                contentColor = TextPrimary
                            ),
                            shape = RoundedCornerShape(RadiusSM),
                            border = BorderStroke(1.dp, Color(0xFFFFE600)),
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text("PAKAI", fontSize = 13.sp, fontFamily = DisplayFont, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Dapatkan diskon spesial dengan memasukkan kode promo.",
                        fontSize = 11.sp,
                        fontFamily = BodyFont,
                        color = TextMuted
                    )
                }
            }

            // Permintaan Khusus section
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Hairline.copy(alpha = 0.5f), RoundedCornerShape(RadiusMD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("PERMINTAAN KHUSUS", fontSize = 11.sp, fontFamily = MonoFont, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(Modifier.height(12.dp))
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { hasHeavyLuggage = !hasHeavyLuggage }
                    ) {
                        Checkbox(
                            checked = hasHeavyLuggage,
                            onCheckedChange = { hasHeavyLuggage = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFFE600), checkmarkColor = TextPrimary)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Ada barang bawaan besar", fontSize = 13.sp, fontFamily = BodyFont, color = TextPrimary)
                    }
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { hasPet = !hasPet }
                    ) {
                        Checkbox(
                            checked = hasPet,
                            onCheckedChange = { hasPet = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFFE600), checkmarkColor = TextPrimary)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Bawa hewan peliharaan", fontSize = 13.sp, fontFamily = BodyFont, color = TextPrimary)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { hasChild = !hasChild }
                    ) {
                        Checkbox(
                            checked = hasChild,
                            onCheckedChange = { hasChild = it },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFFE600), checkmarkColor = TextPrimary)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Bersama anak kecil", fontSize = 13.sp, fontFamily = BodyFont, color = TextPrimary)
                    }

                    Spacer(Modifier.height(16.dp))
                    Text("CATATAN UNTUK DRIVER", fontSize = 11.sp, fontFamily = MonoFont, fontWeight = FontWeight.Bold, color = TextMuted)
                    Spacer(Modifier.height(8.dp))
                    
                    OutlinedTextField(
                        value = driverNote,
                        onValueChange = { driverNote = it },
                        placeholder = { Text("Pesan tambahan...", fontSize = 13.sp, color = TextMuted) },
                        shape = RoundedCornerShape(RadiusSM),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaximDarkGold,
                            unfocusedBorderColor = Hairline,
                            cursorColor = MaximDarkGold
                        ),
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Bottom booking layout
        HorizontalDivider(color = Hairline.copy(alpha = 0.3f), thickness = 1.dp)
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(SpaceMD)
        ) {
            PrimaryButton(
                text = "KONFIRMASI & PESAN",
                onClick = {
                    val num = initialPrice.replace(Regex("[^0-9]"), "").toDoubleOrNull() ?: 18000.0
                    val price = if (isPromoApplied) maxOf(0.0, num - 5000.0) else num
                    
                    if (selectedPaymentMethod == "DOMPET" && walletBalance < price) {
                        android.widget.Toast.makeText(context, "Saldo dompet tidak cukup, silakan gunakan metode tunai atau top up terlebih dahulu.", android.widget.Toast.LENGTH_LONG).show()
                    } else {
                        reportViewModel.createTrip(price)
                        onConfirm()
                    }
                },
                color = Color(0xFFFFE600), // Solid Yellow
                textColor = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFF0F0), // light pink
                    contentColor = Color(0xFFEF4444) // red
                ),
                border = BorderStroke(1.dp, Color(0xFFFCA5A5)), // pink border
                shape = RoundedCornerShape(RadiusSM),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "BATAL",
                    fontSize = 15.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
