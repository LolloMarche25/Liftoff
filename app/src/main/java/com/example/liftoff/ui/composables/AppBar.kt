package com.example.liftoff.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.liftoff.ui.theme.LiftoffBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiftoffTopBar(
    title: String,
    showBackButton: Boolean = false,
    showOptionsIcon: Boolean = false,
    showShareIcon: Boolean = false,
    onBackClick: () -> Unit = {},
    onOptionsClick: () -> Unit = {},
    onShareClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Indietro",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (showShareIcon) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Condividi",
                        tint = Color.White
                    )
                }
            }
            if (showOptionsIcon) {
                IconButton(onClick = onOptionsClick) {
                    Icon(
                        imageVector = Icons.Outlined.MoreVert,
                        contentDescription = "Impostazioni",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LiftoffBackground
        )
    )
}