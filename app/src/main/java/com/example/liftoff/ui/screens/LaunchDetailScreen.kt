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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.liftoff.model.Launch
import com.example.liftoff.navigation.NavigationRoute
import com.example.liftoff.ui.composables.CountdownBox
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffGold
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary
import androidx.core.net.toUri

@Composable
fun LaunchDetailScreen(
    navController: NavHostController,
    launch: Launch,
    detailState: LaunchDetailState
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            LiftoffTopBar(
                title = launch.name,
                showBackButton = true,
                showShareIcon = true,
                onBackClick = { navController.navigateUp() },
                onShareClick = {
                    val sendIntent = android.content.Intent().apply {
                        action = android.content.Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(
                            android.content.Intent.EXTRA_TEXT,
                            "🚀 ${launch.name} - ${launch.rocket} by ${launch.agency}\n" +
                                    "Launching on ${launch.date} from ${launch.location}\n" +
                                    "Follow it on Liftoff!"
                        )
                    }
                    context.startActivity(android.content.Intent.createChooser(sendIntent, "Share launch"))
                }
            )
        },
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
                Spacer(modifier = Modifier.height(8.dp))
                LaunchSiteCard(
                    launch.location,
                    detailState = detailState
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                LaunchDescriptionCard(launch.description)
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
                if (launch.videoUrl.isNotEmpty()) {
                    Button(
                        onClick = {
                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_VIEW,
                                launch.videoUrl.toUri()
                            )
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF0000)
                        ),
                        contentPadding = PaddingValues(vertical = 14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "▶ Watch Live on YouTube",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (!detailState.isCheckedIn) {
                            navController.navigate(
                                NavigationRoute.PersonalNote(
                                    launchId = launch.id,
                                    launchName = launch.name,
                                    launchDate = launch.date,
                                    launchImageUrl = launch.imageUrl
                                )
                            )
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (detailState.isCheckedIn) LiftoffSurfaceVariant else LiftoffPrimary
                    ),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (detailState.isCheckedIn) "Added to Diary! ✓" else "Add to Diary",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DetailHeroSection(launch: Launch) {
    Column {
        AsyncImage(
            model = launch.imageUrl,
            contentDescription = launch.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )
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
                Text(text = "🚀", fontSize = 20.sp)
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CountdownBox(launch.daysLeft, "DAYS", modifier = Modifier.weight(1f))
            CountdownBox(launch.hoursLeft, "HRS", modifier = Modifier.weight(1f))
            CountdownBox(launch.minutesLeft, "MIN", modifier = Modifier.weight(1f))
            CountdownBox(launch.secondsLeft, "SEC", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun LaunchSiteCard(location: String, detailState: LaunchDetailState) {
    val context = LocalContext.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Sito di lancio",
                    tint = LiftoffPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Launch Site",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = location,
                fontSize = 13.sp,
                color = LiftoffTextSecondary
            )

            if (detailState.latitude.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Lat: ${detailState.latitude} | Lon: ${detailState.longitude}",
                    fontSize = 11.sp,
                    color = LiftoffTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (detailState.latitude.isNotEmpty()) {
                Button(
                    onClick = {
                        val uri = android.net.Uri.parse(
                            "geo:${detailState.latitude},${detailState.longitude}?q=${detailState.latitude},${detailState.longitude}"                        )
                        val intent = android.content.Intent(
                            android.content.Intent.ACTION_VIEW, uri
                        )
                        context.startActivity(intent)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LiftoffSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = LiftoffPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Apri in Maps",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(LiftoffBackground, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = LiftoffPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LaunchDescriptionCard(description: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.MilitaryTech,
                    contentDescription = "Descrizione",
                    tint = LiftoffGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Why This Launch Matters",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = LiftoffTextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}