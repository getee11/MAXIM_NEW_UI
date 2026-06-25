package com.example.maxim_project.ui.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.maxim_project.data.viewmodel.ReportViewModel
import com.example.maxim_project.data.viewmodel.RatingUiState

private val POSITIVE_TAGS = listOf("Ramah", "Tepat waktu", "Mengemudi aman", "Kendaraan bersih", "Profesional")
private val NEGATIVE_TAGS = listOf("Minta tarif lebih", "Ugal-ugalan", "Tidak ramah", "Tidak aman", "Kendaraan kotor")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingScreen(
    onBack: () -> Unit,
    onDone: () -> Unit,
    onReport: () -> Unit,
    onCS: () -> Unit,
    reportViewModel: ReportViewModel = viewModel()
) {
    var rating by rememberSaveable { mutableIntStateOf(3) } // Default 3 stars as in first screenshot
    var comment by rememberSaveable { mutableStateOf("") }
    val selectedPositiveTags = remember { mutableStateListOf<String>() }
    val selectedNegativeTags = remember { mutableStateListOf<String>() }
    var isProblemsExpanded by rememberSaveable { mutableStateOf(true) }

    val context = androidx.compose.ui.platform.LocalContext.current
    val driver = reportViewModel.currentDriver
    val trip = reportViewModel.currentTrip

    val ratingUiState by reportViewModel.ratingUiState.collectAsStateWithLifecycle()

    LaunchedEffect(ratingUiState) {
        if (ratingUiState is RatingUiState.Success) {
            android.widget.Toast.makeText(context, "Rating berhasil dikirim!", android.widget.Toast.LENGTH_SHORT).show()
            reportViewModel.resetRatingState()
            onDone()
        } else if (ratingUiState is RatingUiState.Error) {
            val msg = (ratingUiState as RatingUiState.Error).message
            android.widget.Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(
            title = "RATING DRIVER",
            onBack = onBack,
            rightIcon = Icons.Default.Headset,
            onRightClick = onCS
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = SpaceMD, vertical = SpaceSM),
            verticalArrangement = Arrangement.spacedBy(SpaceMD),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            // Driver Photo / Avatar Row
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, CircleShape)
                    .border(2.dp, Color(0xFFFFE600), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(44.dp)
                )
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = driver.namaDriver.uppercase(),
                    fontSize = 18.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "ECONOMY RIDE • ${driver.platNomor}",
                    fontSize = 11.sp,
                    fontFamily = MonoFont,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            // Star Rating Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { i ->
                    val isSelected = i < rating
                    val starColor = if (rating <= 2) Color(0xFFEF4444) else Color(0xFFFFD600)
                    Icon(
                        imageVector = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder,
                        contentDescription = "Star ${i + 1}",
                        tint = if (isSelected) starColor else Hairline.copy(alpha = 0.8f),
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { rating = i + 1 }
                    )
                }
            }

            // Rating description text label
            if (rating > 0) {
                Text(
                    text = when (rating) {
                        1 -> "SANGAT BURUK"
                        2 -> "BURUK"
                        3 -> "CUKUP BAIK"
                        4 -> "BAIK"
                        5 -> "SANGAT BAIK"
                        else -> ""
                    },
                    fontSize = 12.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = if (rating <= 2) Color(0xFFEF4444) else Color(0xFF22C55E),
                    letterSpacing = 1.sp
                )
            }

            Spacer(Modifier.height(8.dp))

            // Positive reviews section (only visible if rating >= 3)
            if (rating >= 3) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "YANG BERJALAN BAIK",
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22C55E),
                        letterSpacing = 0.5.sp
                    )
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        POSITIVE_TAGS.forEach { tag ->
                            val isSelected = tag in selectedPositiveTags
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(RadiusBub))
                                    .background(if (isSelected) Color(0xFFE8F5E9) else Color.White)
                                    .border(
                                        1.dp,
                                        if (isSelected) Color(0xFF22C55E) else Hairline.copy(alpha = 0.5f),
                                        RoundedCornerShape(RadiusBub)
                                    )
                                    .clickable {
                                        if (isSelected) selectedPositiveTags.remove(tag) else selectedPositiveTags.add(tag)
                                    }
                                    .padding(horizontal = SpaceMD, vertical = 8.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 12.sp,
                                    fontFamily = BodyFont,
                                    color = if (isSelected) Color(0xFF22C55E) else TextBody
                                )
                            }
                        }
                    }
                }
            }

            // Collapsible negative reviews section
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isProblemsExpanded = !isProblemsExpanded }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "ADA MASALAH?",
                            fontSize = 11.sp,
                            fontFamily = MonoFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEF4444),
                            letterSpacing = 0.5.sp
                        )
                    }
                    Icon(
                        imageVector = if (isProblemsExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (isProblemsExpanded) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        NEGATIVE_TAGS.forEach { tag ->
                            val isSelected = tag in selectedNegativeTags
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(RadiusBub))
                                    .background(if (isSelected) Color(0xFFFFEBEE) else Color.White)
                                    .border(
                                        1.dp,
                                        if (isSelected) Color(0xFFEF4444) else Hairline.copy(alpha = 0.5f),
                                        RoundedCornerShape(RadiusBub)
                                    )
                                    .clickable {
                                        if (isSelected) selectedNegativeTags.remove(tag) else selectedNegativeTags.add(tag)
                                    }
                                    .padding(horizontal = SpaceMD, vertical = 8.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 12.sp,
                                    fontFamily = BodyFont,
                                    color = if (isSelected) Color(0xFFEF4444) else TextBody
                                )
                            }
                        }
                    }
                }
            }

            // Lapor Driver card
            Card(
                shape = RoundedCornerShape(RadiusMD),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5F5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(RadiusMD))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "LAPORKAN DRIVER",
                            fontSize = 13.sp,
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEF4444)
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Laporkan pelanggaran driver agar ditindaklanjuti tim Maxim.",
                        fontSize = 11.sp,
                        fontFamily = BodyFont,
                        color = TextBody
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onReport,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF4444),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(RadiusSM),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                    ) {
                        Text(
                            text = "LAPORKAN SEKARANG",
                            fontSize = 12.sp,
                            fontFamily = DisplayFont,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Comment text area
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "KOMENTAR (OPSIONAL)",
                    fontSize = 10.sp,
                    fontFamily = MonoFont,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    placeholder = { Text("Tulis komentar (opsional)", fontSize = 13.sp, color = TextMuted) },
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

        // Bottom control buttons
        HorizontalDivider(color = Hairline.copy(alpha = 0.3f), thickness = 1.dp)
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(SpaceMD)
        ) {
            PrimaryButton(
                text = if (ratingUiState is RatingUiState.Loading) "MENGIRIM..." else "KIRIM RATING",
                onClick = {
                    reportViewModel.submitRating(
                        rating = rating,
                        comment = comment,
                        positiveTags = selectedPositiveTags,
                        negativeTags = selectedNegativeTags
                    )
                },
                color = Color(0xFFFFE600), // Solid Yellow
                textColor = TextPrimary,
                enabled = rating > 0 && ratingUiState !is RatingUiState.Loading
            )
        }
    }
}
