package com.example.liftoff.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.liftoff.ui.composables.LiftoffBottomBar
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffGold
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffTextSecondary
import com.example.liftoff.ui.utils.NotificationHelper

@Composable
fun HomeScreen(
    navController: NavHostController,
    nextLaunch: Launch,
    upcomingLaunches: List<Launch>,
    isNextLaunchNotified: Boolean,
    onLaunchClick: (Launch) -> Unit,
    onNotifyClick: () -> Unit,
    onOptionsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            LiftoffTopBar(
                title = "Next Launch",
                showOptionsIcon = true,
                onOptionsClick = onOptionsClick
            )
        },
        bottomBar = {
            LiftoffBottomBar(navController = navController)
        },
        containerColor = LiftoffBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                NextLaunchCard(
                    launch = nextLaunch,
                    isNotified = isNextLaunchNotified,
                    onClick = { onLaunchClick(nextLaunch) },
                    onNotifyClick = onNotifyClick
                )
            }
            item {
                UpcomingLaunchesSection(
                    launches = upcomingLaunches,
                    onLaunchClick = onLaunchClick,
                    onViewAllClick = { navController.navigate(NavigationRoute.Launches) }
                )
            }
        }
    }
}

@Composable
fun NextLaunchCard(
    launch: Launch,
    isNotified: Boolean,
    onClick: () -> Unit,
    onNotifyClick: () -> Unit
) {
    val context = LocalContext.current

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            NotificationHelper.scheduleNotification(context, launch.name, launch.netUtc)
            NotificationHelper.showNotification(context, launch.name)
            onNotifyClick()
        }
    }
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Box {
                AsyncImage(
                    model = launch.imageUrl,
                    contentDescription = launch.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Text(
                    text = launch.status,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(LiftoffGold, RoundedCornerShape(20.dp))
                        .padding(12.dp, 4.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = launch.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${launch.rocket} • ${launch.agency}",
                        fontSize = 13.sp,
                        color = LiftoffTextSecondary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "\uD83D\uDE80", fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CountdownBox(launch.daysLeft, "DAYS", modifier = Modifier.weight(1f))
                CountdownBox(launch.hoursLeft, "HRS",  modifier = Modifier.weight(1f))
                CountdownBox(launch.minutesLeft, "MIN",  modifier = Modifier.weight(1f))
                CountdownBox(launch.secondsLeft, "SEC",  modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Posizione",
                    modifier = Modifier.size(16.dp),
                    tint = LiftoffPrimary,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = launch.location,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!isNotified) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            NotificationHelper.scheduleNotification(context, launch.name, launch.netUtc)
                            NotificationHelper.showNotification(context, launch.name)
                            onNotifyClick()
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LiftoffPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isNotified) "You'll be reminded" else "Remind Me",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun UpcomingLaunchesSection(
    launches: List<Launch>,
    onLaunchClick: (Launch) -> Unit,
    onViewAllClick: () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Upcoming Launches",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            TextButton(onClick = onViewAllClick) {
                Text(
                    text = "View All >",
                    fontSize = 13.sp,
                    color = LiftoffPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(launches) { launch ->
                UpcomingLaunchCard(launch, onClick = { onLaunchClick(launch) })
            }
        }
    }
}

@Composable
fun UpcomingLaunchCard(launch: Launch, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.width(160.dp)
    ) {
        Box {
            AsyncImage(
                model = launch.imageUrl,
                contentDescription = launch.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
            )
            Text(
                text = launch.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
        }
    }
}