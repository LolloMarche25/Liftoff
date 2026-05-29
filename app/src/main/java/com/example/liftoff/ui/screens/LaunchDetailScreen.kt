package com.example.liftoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftoff.model.Launch
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun LaunchDetailScreen(launch: Launch) {
    Scaffold(
        containerColor = LiftoffBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                DetailHeroSection(launch)
            }
            item {
                Text(
                    text = "Coming soon...",
                    color = LiftoffTextSecondary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DetailHeroSection(launch: Launch) {
    Column {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(LiftoffSurfaceVariant)
            )

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(LiftoffSurface.copy(alpha = 0.8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Indietro",
                        tint = Color.White
                    )
                }
            }

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(LiftoffSurface.copy(alpha = 0.8f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Condividi",
                        tint = Color.White
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = launch.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${launch.rocket} • ${launch.agency}",
                    fontSize = 14.sp,
                    color = LiftoffTextSecondary
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "\uD83D\uDE80", fontSize = 20.sp)
            }
        }
    }
}