package com.example.maxim_project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.maxim_project.ui.theme.*

@Composable
fun MaximNavBar(
    title: String = "",
    onBack: (() -> Unit)? = null,
    rightIcon: ImageVector? = null,
    onRightClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Canvas)
    ) {
        Spacer(Modifier.height(StatusBarHeight))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(NavBarHeight)
                .padding(horizontal = SpaceXXS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: back button
            Box(modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center) {
                if (onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                }
            }
            // Center: title
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            // Right: action icon
            Box(modifier = Modifier.width(48.dp), contentAlignment = Alignment.Center) {
                if (rightIcon != null) {
                    IconButton(onClick = { onRightClick?.invoke() }) {
                        Icon(rightIcon, contentDescription = null, tint = TextPrimary)
                    }
                }
            }
        }
        HorizontalDivider(color = Hairline, thickness = 1.dp)
    }
}
