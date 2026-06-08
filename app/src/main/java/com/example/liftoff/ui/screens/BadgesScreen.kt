package com.example.liftoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftoff.model.Badge
import com.example.liftoff.ui.composables.LiftoffBottomBar
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun BadgesScreen(
    navController: NavHostController,
    badges: List<Badge>,
    userLevel: String,
    userPoints: Int,
    progressCurrent: Int,
    progressTotal: Int
) {
    Scaffold(
        topBar = { LiftoffTopBar(title = "Badges") },
        bottomBar = { LiftoffBottomBar(navController = navController) },
        containerColor = LiftoffBackground
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                LevelCard(
                    level = userLevel,
                    points = userPoints,
                    progressCurrent = progressCurrent,
                    progressTotal = progressTotal
                )
            }
            item {
                BadgesGrid(badges = badges)
            }
        }
    }
}

@Composable
fun LevelCard(
    level: String,
    points: Int,
    progressCurrent: Int,
    progressTotal: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = level,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "$points points",
                        fontSize = 14.sp,
                        color = LiftoffTextSecondary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(LiftoffSurfaceVariant, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "\uD83C\uDFC6", fontSize = 22.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Progress to next level",
                    fontSize = 12.sp,
                    color = LiftoffTextSecondary
                )
                Text(
                    text = "$progressCurrent/$progressTotal",
                    fontSize = 12.sp,
                    color = LiftoffPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progressCurrent.toFloat() / progressTotal.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = LiftoffPrimary,
                trackColor = LiftoffSurfaceVariant
            )
        }
    }
}

@Composable
fun BadgesGrid(badges: List<Badge>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        badges.chunked(3).forEach { rowBadges ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                rowBadges.forEach { badge ->
                    BadgeCell(
                        badge = badge,
                        modifier = Modifier.weight(1f)
                    )
                }
                repeat(3 - rowBadges.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BadgeCell(badge: Badge, modifier: Modifier = Modifier) {
    val isUnlocked = badge.isUnlocked

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) LiftoffSurface else LiftoffBackground
        ),
        modifier = modifier.height(160.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(LiftoffSurfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isUnlocked) {
                    Text(text = badge.emoji, fontSize = 22.sp)
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = LiftoffTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = badge.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isUnlocked) Color.White else LiftoffTextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = badge.description,
                fontSize = 10.sp,
                color = LiftoffTextSecondary,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}