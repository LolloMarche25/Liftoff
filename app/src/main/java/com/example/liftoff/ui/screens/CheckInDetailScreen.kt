package com.example.liftoff.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.liftoff.model.CheckIn
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun CheckInDetailScreen(
    navController: NavHostController,
    checkIn: CheckIn
) {
    Scaffold(
        topBar = {
            LiftoffTopBar(
                title = "Launch Memory",
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        },
        containerColor = LiftoffBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (checkIn.photoUri.isNotEmpty()) {
                AsyncImage(
                    model = checkIn.photoUri,
                    contentDescription = "Foto ricordo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            } else if (checkIn.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = checkIn.imageUrl,
                    contentDescription = checkIn.launchName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = checkIn.launchName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = LiftoffPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = checkIn.date,
                            fontSize = 13.sp,
                            color = LiftoffTextSecondary
                        )
                    }
                }
            }

            if (checkIn.note.isNotEmpty()) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "My thoughts ✨",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = LiftoffPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = checkIn.note,
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}