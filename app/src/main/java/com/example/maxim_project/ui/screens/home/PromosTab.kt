package com.example.maxim_project.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.PromoCard
import com.example.maxim_project.ui.components.PromoData
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

private val PROMOS = listOf(
    PromoData("Diskon 30% Motor", "Berlaku untuk semua perjalanan motor dalam kota", "MOTOR30", "30 Jun 2026", MaximYellow, YellowLight),
    PromoData("Gratis Ongkir Kurir", "Pengiriman gratis untuk paket di bawah 5kg", "FREEONGKIR", "28 Jun 2026", Blue, BlueLight),
    PromoData("Cashback Makanan 15%", "Min. order Rp 30.000 dari restoran mana saja", "MAKAN15", "25 Jun 2026", Terracotta, TerracottaLight),
    PromoData("Premium Ride 50%", "Diskon mobil premium pertama kali", "PREMIUM50", "31 Jul 2026", Purple, PurpleLight),
    PromoData("Top Up Bonus 20%", "Top up Maxim Pay min Rp 100.000", "TOPUP20", "15 Jul 2026", Green, GreenLight),
)

@Composable
fun PromosTab() {
    var promoCode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        Spacer(Modifier.height(StatusBarHeight + SpaceSM))
        Text(
            "PROMO",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = SpaceMD)
        )
        Spacer(Modifier.height(SpaceMD))

        // Input kode promo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMD),
            horizontalArrangement = Arrangement.spacedBy(SpaceXS)
        ) {
            OutlinedTextField(
                value = promoCode,
                onValueChange = { promoCode = it.uppercase() },
                placeholder = { Text("Masukkan kode promo") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            PrimaryButton(
                text = "Pakai",
                onClick = { promoCode = "" },
                modifier = Modifier.width(90.dp),
                enabled = promoCode.isNotEmpty()
            )
        }

        Spacer(Modifier.height(SpaceMD))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = SpaceMD, vertical = SpaceXS),
            verticalArrangement = Arrangement.spacedBy(SpaceSM)
        ) {
            items(PROMOS) { promo ->
                PromoCard(promo = promo, onCopy = { })
            }
            item { Spacer(Modifier.height(BottomBarHeight + SpaceMD)) }
        }
    }
}
