package com.example.maxim_project.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.components.PromoCard
import com.example.maxim_project.ui.components.PromoData
import com.example.maxim_project.ui.theme.*

private val PROMOS = listOf(
    PromoData("BIKE", "50% untuk perjalanan ojek pertama", "Min. Rp 20.000", "MAXFIRST50", "30 JUN 2025", Color(0xFF6C5A25), YellowLight),
    PromoData("CAR", "Diskon Rp 25.000 untuk taksi", "Min. Rp 50.000", "CAR25K", "29 JUN 2025", Color(0xFFFFD600), YellowLight),
    PromoData("FOOD", "Gratis ongkir makanan Rp 15.000", "Tanpa minimum", "FOODFREE", "25 JUN 2025", Color(0xFF8C5C4C), TerracottaLight),
    PromoData("DELIVERY", "Diskon 30% pengiriman barang", "Min. Rp 15.000", "SHIP30", "28 JUN 2025", Color(0xFF3B82F6), BlueLight),
)

@Composable
fun PromosTab() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        Spacer(Modifier.height(StatusBarHeight + SpaceSM))
        
        // Promo Header
        Column(modifier = Modifier.padding(horizontal = SpaceMD)) {
            Text(
                text = "PROMO",
                fontSize = 24.sp,
                fontFamily = DisplayFont,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Hemat lebih banyak dengan kode promo eksklusif.",
                fontSize = 13.sp,
                fontFamily = BodyFont,
                color = TextMuted
            )
        }

        Spacer(Modifier.height(SpaceMD))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = SpaceMD, vertical = SpaceXS),
            verticalArrangement = Arrangement.spacedBy(SpaceSM)
        ) {
            items(PROMOS) { promo ->
                PromoCard(promo = promo, onCopy = {
                    Toast.makeText(context, "Kode promo ${promo.code} berhasil disalin!", Toast.LENGTH_SHORT).show()
                })
            }
            item { Spacer(Modifier.height(BottomBarHeight + SpaceMD)) }
        }
    }
}

