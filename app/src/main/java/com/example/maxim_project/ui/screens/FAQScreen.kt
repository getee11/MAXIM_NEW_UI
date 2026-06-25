package com.example.maxim_project.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.components.SecondaryButton
import com.example.maxim_project.ui.theme.*

data class FAQCategory(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val items: List<FAQItemData>
)

data class FAQItemData(
    val question: String,
    val answer: String
)

val FAQ_DATA = listOf(
    FAQCategory(
        title = "Pemesanan",
        icon = Icons.Default.DirectionsCar,
        color = Blue,
        items = listOf(
            FAQItemData("Bagaimana cara memesan ojek atau taksi?", "Buka halaman utama -> ketuk layanan Bike atau Car -> masukkan lokasi penjemputan dan tujuan -> pilih tipe kendaraan -> konfirmasi pesanan. Driver terdekat akan otomatis ditemukan."),
            FAQItemData("Bisakah saya menjadwalkan perjalanan?", "Ya, tersedia opsi 'Jadwalkan Perjalanan' di layar pilih kendaraan. Pesanan terjadwal dapat dibuat hingga 7 hari ke depan."),
            FAQItemData("Berapa lama driver akan menjemput saya?", "Estimasi penjemputan biasanya 3-10 menit tergantung jarak dan ketersediaan driver di sekitar lokasi Anda."),
            FAQItemData("Apa yang harus dilakukan jika driver membatalkan?", "Jika driver membatalkan, pesanan akan otomatis dicari ulang ke driver lain tanpa biaya tambahan. Saldo Anda tidak akan terpotong.")
        )
    ),
    FAQCategory(
        title = "Pembayaran",
        icon = Icons.Default.Wallet,
        color = MaximDarkGold,
        items = listOf(
            FAQItemData("Apa saja metode pembayaran yang tersedia?", "Maxim mendukung: Maxim Wallet (saldo digital), Tunai (bayar langsung ke driver), dan Kartu Debit/Kredit. Disarankan menggunakan Maxim Wallet untuk kemudahan dan promo eksklusif."),
            FAQItemData("Bagaimana cara top up Maxim Wallet?", "Masuk ke menu Profil -> Maxim Wallet -> Top Up. Tersedia nominal Rp 50.000, 100.000, 200.000, dan 500.000 melalui transfer bank atau dompet digital."),
            FAQItemData("Berapa lama proses refund jika ada kesalahan bayar?", "Refund biasanya diproses dalam 3-7 hari kerja kembali ke metode pembayaran asal. Hubungi CS dengan menyertakan nomor order untuk mempercepat proses."),
            FAQItemData("Mengapa saya dikenakan biaya berbeda dari estimasi?", "Biaya final dapat berbeda dari estimasi karena: perubahan rute, kemacetan parah, atau permintaan khusus (barang besar, dll). Detail selalu tersedia di struk perjalanan.")
        )
    ),
    FAQCategory(
        title = "Driver",
        icon = Icons.Default.Person,
        color = Terracotta,
        items = listOf(
            FAQItemData("Bagaimana cara menghubungi driver saat perjalanan?", "Saat perjalanan aktif, ketuk ikon Telepon atau Chat di layar tracking untuk menghubungi driver secara langsung."),
            FAQItemData("Apa yang harus dilakukan jika driver tidak datang?", "Tunggu maksimal 5 menit setelah driver tiba. Jika driver tidak muncul, hubungi driver melalui fitur Chat/Telepon. Jika tidak ada respons, batalkan dan pesan ulang."),
            FAQItemData("Bagaimana cara melaporkan driver bermasalah?", "Setelah perjalanan selesai -> beri rating 1-2 bintang -> ketuk 'Ada Masalah?' -> pilih jenis pelanggaran -> kirim laporan. Tim Maxim akan menindaklanjuti dalam 1x24 jam.")
        )
    ),
    FAQCategory(
        title = "Promo & Kode",
        icon = Icons.Default.LocalOffer,
        color = Purple,
        items = listOf(
            FAQItemData("Bagaimana cara menggunakan kode promo?", "Di layar konfirmasi pesanan, scroll ke bawah -> cari bagian 'Kode Promo' -> masukkan kode -> ketuk 'Pakai'. Diskon akan langsung teraplikasi ke total harga."),
            FAQItemData("Kode promo saya tidak berfungsi, kenapa?", "Pastikan: (1) kode diketik dengan benar (capslock), (2) promo belum kadaluarsa, (3) memenuhi syarat minimum pemesanan, (4) belum digunakan sebelumnya. Satu akun hanya boleh menggunakan satu promo per perjalanan."),
            FAQItemData("Apakah promo bisa digabungkan?", "Tidak, hanya satu kode promo yang dapat digunakan per pemesanan. Pilih promo dengan nilai diskon tertinggi untuk menghemat lebih banyak.")
        )
    ),
    FAQCategory(
        title = "Akun & Keamanan",
        icon = Icons.Default.Shield,
        color = Green,
        items = listOf(
            FAQItemData("Bagaimana cara mengubah nomor telepon?", "Perubahan nomor telepon memerlukan verifikasi keamanan. Masuk ke Profil -> Edit Profil -> Nomor Telepon, lalu ikuti langkah verifikasi OTP ke nomor baru."),
            FAQItemData("Saya lupa kode OTP, apa yang harus dilakukan?", "Di layar verifikasi OTP, tunggu hingga timer habis -> ketuk 'Kirim Ulang'. Kode baru akan dikirim ke nomor yang terdaftar. Pastikan sinyal ponsel Anda baik."),
            FAQItemData("Bagaimana cara menghapus akun saya?", "Penghapusan akun bersifat permanen dan tidak dapat dibatalkan. Hubungi CS Maxim melalui aplikasi dengan subject 'Penghapusan Akun' untuk diproses dalam 7-14 hari kerja.")
        )
    )
)

@Composable
fun FAQScreen(
    onBack: () -> Unit,
    onCS: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQ by remember { mutableStateOf("") }
    var openItemKey by remember { mutableStateOf<String?>(null) }

    val allItems = remember {
        FAQ_DATA.flatMap { cat ->
            cat.items.map { item ->
                Triple(item, cat.title, cat.color)
            }
        }
    }

    val filteredItems = remember(searchQ) {
        if (searchQ.length > 1) {
            allItems.filter { (item, _, _) ->
                item.question.contains(searchQ, ignoreCase = true) ||
                        item.answer.contains(searchQ, ignoreCase = true)
            }
        } else {
            null
        }
    }

    Scaffold(
        topBar = {
            MaximNavBar(
                title = "Bantuan & FAQ",
                onBack = onBack
            )
        },
        containerColor = Canvas,
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input Field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMD, vertical = SpaceSM)
            ) {
                OutlinedTextField(
                    value = searchQ,
                    onValueChange = { searchQ = it },
                    placeholder = { Text("Cari pertanyaan...", style = MaterialTheme.typography.bodyMedium, color = TextMuted) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        if (searchQ.isNotEmpty()) {
                            IconButton(onClick = { searchQ = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextMuted)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(RadiusBub),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = MaximYellow,
                        unfocusedBorderColor = Hairline,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = SpaceMD)
            ) {
                if (filteredItems != null) {
                    // Search results view
                    Text(
                        text = "${filteredItems.size} hasil untuk \"$searchQ\"",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMuted,
                        modifier = Modifier.padding(bottom = SpaceSM)
                    )

                    if (filteredItems.isEmpty()) {
                        // Empty search results
                        Card(
                            shape = RoundedCornerShape(RadiusMD),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.5.dp, Hairline),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = SpaceMD)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(SpaceLG),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.HelpOutline,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(SpaceSM))
                                Text(
                                    text = "Pertanyaan Tidak Ditemukan",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(SpaceXS))
                                Text(
                                    text = "Kami tidak menemukan jawaban untuk pertanyaanmu. Tim CS kami siap membantu secara langsung.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextBody,
                                    modifier = Modifier.padding(horizontal = SpaceSM)
                                )
                                Spacer(modifier = Modifier.height(SpaceMD))
                                PrimaryButton(
                                    text = "Hubungi CS Sekarang",
                                    onClick = onCS,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(SpaceXS)) {
                            filteredItems.forEachIndexed { i, (item, _, color) ->
                                val key = "search-$i"
                                FAQItem(
                                    question = item.question,
                                    answer = item.answer,
                                    accent = color,
                                    isOpen = openItemKey == key,
                                    onToggle = {
                                        openItemKey = if (openItemKey == key) null else key
                                    }
                                )
                            }
                        }
                    }
                } else {
                    // Default Category Accordions
                    FAQ_DATA.forEachIndexed { ci, cat ->
                        Column(modifier = Modifier.padding(bottom = SpaceMD)) {
                            // Category Header
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(SpaceXS),
                                modifier = Modifier.padding(vertical = SpaceSM)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(RadiusXS))
                                        .background(cat.color.copy(alpha = 0.1f))
                                ) {
                                    Icon(
                                        cat.icon,
                                        contentDescription = null,
                                        tint = cat.color,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Text(
                                    text = cat.title.uppercase(),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = cat.color
                                )
                            }

                            // Items in this category
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                cat.items.forEachIndexed { ii, item ->
                                    val key = "$ci-$ii"
                                    FAQItem(
                                        question = item.question,
                                        answer = item.answer,
                                        accent = cat.color,
                                        isOpen = openItemKey == key,
                                        onToggle = {
                                            openItemKey = if (openItemKey == key) null else key
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(SpaceSM))

                // CS Escalation Box
                Card(
                    shape = RoundedCornerShape(RadiusMD),
                    colors = CardDefaults.cardColors(containerColor = Blue.copy(alpha = 0.05f)),
                    border = BorderStroke(1.5.dp, Blue.copy(alpha = 0.2f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SpaceLG)
                ) {
                    Column(modifier = Modifier.padding(SpaceMD)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(SpaceXS)
                        ) {
                            Icon(
                                Icons.Default.Headphones,
                                contentDescription = null,
                                tint = Blue,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "MASIH BUTUH BANTUAN?",
                                style = MaterialTheme.typography.labelMedium,
                                color = Blue
                            )
                        }
                        Spacer(modifier = Modifier.height(SpaceXS))
                        Text(
                            text = "Tidak menemukan jawaban di atas? Tim Customer Service Maxim siap membantu kamu 24/7 dengan waktu respons rata-rata kurang dari 1 menit.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextBody
                        )
                        Spacer(modifier = Modifier.height(SpaceMD))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(SpaceSM)
                        ) {
                            PrimaryButton(
                                text = "Chat CS",
                                onClick = onCS,
                                color = Blue,
                                textColor = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                            SecondaryButton(
                                text = "Telepon CS",
                                onClick = { /* Phone dial action */ },
                                color = Blue,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FAQItem(
    question: String,
    answer: String,
    accent: Color,
    isOpen: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(RadiusMD),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, if (isOpen) accent else Hairline),
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMD, vertical = SpaceSM),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (isOpen) accent else TextMuted
                )
            }

            AnimatedVisibility(
                visible = isOpen,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(accent.copy(alpha = 0.04f))
                        .padding(horizontal = SpaceMD, vertical = SpaceSM)
                ) {
                    HorizontalDivider(color = Hairline)
                    Spacer(modifier = Modifier.height(SpaceXS))
                    Text(
                        text = answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextBody,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
