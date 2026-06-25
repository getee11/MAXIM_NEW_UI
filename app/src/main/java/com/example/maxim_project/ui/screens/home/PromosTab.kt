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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.example.maxim_project.data.viewmodel.ReportViewModel
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.components.PromoCard
import com.example.maxim_project.ui.components.PromoData
import com.example.maxim_project.ui.theme.*

@Composable
fun PromosTab(
    reportViewModel: ReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val allPromos by reportViewModel.allPromos.collectAsStateWithLifecycle()

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
            items(allPromos) { dbPromo ->
                val promo = PromoData(
                    category = "PROMO", 
                    title = dbPromo.title, 
                    desc = dbPromo.desc, 
                    code = dbPromo.promoId, 
                    expires = dbPromo.validUntil, 
                    accent = Color(0xFF6C5A25), 
                    tintBg = YellowLight
                )
                PromoCard(promo = promo, onCopy = {
                    Toast.makeText(context, "Kode promo ${promo.code} berhasil disalin!", Toast.LENGTH_SHORT).show()
                })
            }
            item { Spacer(Modifier.height(BottomBarHeight + SpaceMD)) }
        }
    }
}

