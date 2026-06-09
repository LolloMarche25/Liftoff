package com.example.liftoff.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.liftoff.model.CheckIn
import com.example.liftoff.ui.composables.LiftoffBottomBar
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun CheckInsScreen(
    navController: NavHostController,
    checkIns: List<CheckIn>,
    onCheckInClick: (CheckIn) -> Unit
) {
    Scaffold(
        topBar = {
            LiftoffTopBar(
                title = "Space Diary"
            )
        },
        bottomBar = { LiftoffBottomBar(navController = navController) },
        containerColor = LiftoffBackground
    ) { innerPadding ->
        if (checkIns.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No check-ins yet!\nStart tracking launches.",
                    color = LiftoffTextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(checkIns) { checkIn ->
                    CheckInCard(
                        checkIn = checkIn,
                        onClick = { onCheckInClick(checkIn)}
                        )
                }
            }
        }
    }
}

@Composable
fun CheckInCard(
    checkIn: CheckIn,
    onClick: () -> Unit
    ) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column {
            if (checkIn.photoUri.isNotEmpty()) {
                AsyncImage(
                    model = checkIn.photoUri,
                    contentDescription = "Foto check-in",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
            } else if (checkIn.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = checkIn.imageUrl,
                    contentDescription = checkIn.launchName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(LiftoffBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "\uD83D\uDE80", fontSize = 36.sp)
                }
            }

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = checkIn.launchName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = checkIn.date,
                    fontSize = 10.sp,
                    color = LiftoffTextSecondary
                )
            }
        }
    }
}