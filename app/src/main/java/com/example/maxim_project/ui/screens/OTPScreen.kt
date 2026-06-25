package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximNavBar
import com.example.maxim_project.ui.components.MaximTextButton
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun OTPScreen(onBack: () -> Unit, onVerified: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var timer by remember { mutableIntStateOf(60) }
    var canResend by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timer > 0) {
            delay(1000)
            timer--
        }
        canResend = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
    ) {
        MaximNavBar(title = "Verifikasi", onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceLG, vertical = SpaceXL),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Masukkan kode OTP",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary
            )
            Spacer(Modifier.height(SpaceXS))
            Text(
                "Kode dikirim ke +62 812 •••• ••90",
                style = MaterialTheme.typography.bodyMedium,
                color = TextBody,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(SpaceXL))

            // OTP boxes
            BasicTextField(
                value = code,
                onValueChange = {
                    if (it.length <= 6 && it.all { c -> c.isDigit() }) code = it
                    if (it.length == 6) onVerified()
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SpaceSM),
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(6) { i ->
                            val char = code.getOrNull(i)
                            val isFocused = i == code.length
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(RadiusSM))
                                    .background(if (char != null) YellowLight else Surface)
                                    .border(
                                        2.dp,
                                        when {
                                            char != null -> MaximYellow
                                            isFocused -> MaximDarkGold
                                            else -> Hairline
                                        },
                                        RoundedCornerShape(RadiusSM)
                                    )
                            ) {
                                Text(
                                    text = char?.toString() ?: "",
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = TextPrimary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            )

            Spacer(Modifier.height(SpaceLG))

            // Timer / Resend
            if (canResend) {
                MaximTextButton(
                    text = "Kirim Ulang Kode",
                    onClick = {
                        timer = 60
                        canResend = false
                        code = ""
                    }
                )
            } else {
                Text(
                    "Kirim ulang dalam ${timer}s",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextMuted
                )
            }

            Spacer(Modifier.height(SpaceXL))

            PrimaryButton(
                text = "Verifikasi",
                onClick = onVerified,
                enabled = code.length == 6
            )
        }
    }
}
