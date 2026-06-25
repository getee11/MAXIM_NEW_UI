package com.example.maxim_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.components.MaximTextButton
import com.example.maxim_project.ui.components.PrimaryButton
import com.example.maxim_project.ui.theme.*

@Composable
fun AuthScreen(onNext: (phone: String, password: String, name: String, isLogin: Boolean) -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .padding(horizontal = SpaceLG),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(StatusBarHeight + SpaceXL))

        // Logo
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(RadiusMD))
                .background(YellowLight)
                .border(2.dp, MaximYellow, RoundedCornerShape(RadiusMD))
        ) {
            Text("M", style = MaterialTheme.typography.displaySmall, color = MaximDarkGold)
        }

        Spacer(Modifier.height(SpaceLG))
        Text(
            if (isLogin) "MASUK" else "DAFTAR AKUN",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary
        )
        Spacer(Modifier.height(SpaceXS))
        Text(
            if (isLogin) "Masuk dengan nomor telepon Anda" else "Buat akun baru untuk mulai",
            style = MaterialTheme.typography.bodyMedium,
            color = TextBody,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(SpaceXL))

        // Phone input
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it.filter { c -> c.isDigit() }.take(13) },
            label = { Text("Nomor Telepon") },
            placeholder = { Text("812 3456 7890") },
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = SpaceSM)
                ) {
                    Text("+62", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
                    Spacer(Modifier.width(SpaceXS))
                    Box(Modifier.width(1.dp).height(20.dp).background(Hairline))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(RadiusSM),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaximYellow,
                unfocusedBorderColor = Hairline,
                cursorColor = MaximDarkGold
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(Modifier.height(SpaceMD))
        
        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("Masukkan password") },
            shape = RoundedCornerShape(RadiusSM),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaximYellow,
                unfocusedBorderColor = Hairline,
                cursorColor = MaximDarkGold
            ),
            singleLine = true,
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        // Register-only fields
        if (!isLogin) {
            Spacer(Modifier.height(SpaceMD))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Lengkap") },
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(SpaceMD))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email (opsional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(RadiusSM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaximYellow,
                    unfocusedBorderColor = Hairline,
                    cursorColor = MaximDarkGold
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(SpaceLG))

        PrimaryButton(
            text = if (isLogin) "Masuk" else "Daftar & Kirim OTP",
            onClick = { onNext(phone, password, name, isLogin) },
            enabled = phone.length >= 9 && password.isNotEmpty() && (isLogin || name.isNotEmpty())
        )

        Spacer(Modifier.height(SpaceMD))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                if (isLogin) "Belum punya akun?" else "Sudah punya akun?",
                style = MaterialTheme.typography.bodySmall,
                color = TextBody
            )
            MaximTextButton(
                text = if (isLogin) "Daftar" else "Masuk",
                onClick = { isLogin = !isLogin }
            )
        }

        Spacer(Modifier.weight(1f))

        Text(
            "Dengan melanjutkan, Anda menyetujui\nSyarat Penggunaan & Kebijakan Privasi",
            style = MaterialTheme.typography.bodySmall,
            color = TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = SpaceXL)
        )
    }
}
