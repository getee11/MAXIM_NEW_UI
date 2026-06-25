package com.example.maxim_project.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.maxim_project.ui.theme.*

data class PromoData(
    val category: String,
    val title: String,
    val desc: String,
    val code: String,
    val expires: String,
    val accent: Color,
    val tintBg: Color
)

@Composable
fun PromoCard(
    promo: PromoData,
    onCopy: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(RadiusMD)
    val buttonShape = RoundedCornerShape(12.dp)
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .background(Color.White)
            .border(1.dp, Hairline.copy(alpha = 0.5f), cardShape)
    ) {
        // Thick Left border penanda matching accent color
        Box(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .background(promo.accent)
                .align(Alignment.CenterStart)
        )

        // Main Card Content Column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Category & Expiry Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = promo.category.uppercase(),
                    fontSize = 11.sp,
                    fontFamily = DisplayFont,
                    fontWeight = FontWeight.Bold,
                    color = promo.accent
                )
                Text(
                    text = "S/D ${promo.expires.uppercase()}",
                    fontSize = 10.sp,
                    fontFamily = MonoFont,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )
            }

            // Title (Large Bold)
            Text(
                text = promo.title,
                fontSize = 16.sp,
                fontFamily = DisplayFont,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 20.sp
            )

            // Subtitle / Min Spending details
            Text(
                text = promo.desc,
                fontSize = 12.sp,
                fontFamily = BodyFont,
                color = TextMuted
            )

            Spacer(Modifier.height(2.dp))

            // Action Row: Copy Box & Pakai Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Copy Code Box Container
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(promo.tintBg)
                        .border(1.dp, promo.accent.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .clickable {
                            clipboardManager.setText(AnnotatedString(promo.code))
                            Toast.makeText(context, "Kode ${promo.code} disalin!", Toast.LENGTH_SHORT).show()
                            onCopy()
                        }
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = promo.code,
                        fontSize = 11.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        letterSpacing = 1.sp
                    )
                    Icon(
                        imageVector = Icons.Outlined.ContentCopy,
                        contentDescription = "Copy code",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // PAKAI Button
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(90.dp)
                        .height(38.dp)
                        .clip(buttonShape)
                        .background(promo.accent)
                        .clickable {
                            Toast.makeText(context, "Kode promo ${promo.code} berhasil diterapkan!", Toast.LENGTH_SHORT).show()
                        }
                ) {
                    Text(
                        text = "PAKAI",
                        fontSize = 12.sp,
                        fontFamily = MonoFont,
                        fontWeight = FontWeight.Bold,
                        color = if (promo.accent == Color(0xFFFFD600) || promo.accent == MaximYellow) TextPrimary else Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}
