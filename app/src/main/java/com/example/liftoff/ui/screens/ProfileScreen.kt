package com.example.liftoff.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftoff.navigation.NavigationRoute
import com.example.liftoff.ui.composables.LiftoffBottomBar
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffError
import com.example.liftoff.ui.theme.LiftoffGold
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun ProfileScreen(
    navController: NavHostController,
    username: String,
    email: String,
    avatarEmoji: String,
    launchesFollowed: Int,
    checkInsCount: Int,
    badgesUnlocked: Int,
    onLogoutClick: () -> Unit,
    onAvatarClick: (String) -> Unit
) {
    var showAvatarDialog by remember { mutableStateOf(false) }

    if (showAvatarDialog) {
        AvatarPickerDialog(
            onDismiss = { showAvatarDialog = false },
            onAvatarSelected = { emoji ->
                onAvatarClick(emoji)
                showAvatarDialog = false
            }
        )
    }

    Scaffold(
        topBar = { LiftoffTopBar(title = "Profile") },
        bottomBar = { LiftoffBottomBar(navController = navController) },
        containerColor = LiftoffBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileCard(
                username = username,
                email = email,
                avatarEmoji = avatarEmoji,
                launchesFollowed = launchesFollowed,
                checkInsCount = checkInsCount,
                badgesUnlocked = badgesUnlocked,
                onAvatarClick = { showAvatarDialog = true }
            )
            ProfileMenu(
                onSpaceDiaryClick = { navController.navigate(NavigationRoute.Diary) },
                onAchievementsClick = { navController.navigate(NavigationRoute.Badges) },
                onSettingsClick = { navController.navigate(NavigationRoute.Settings) }
            )
            AboutCard()
            LogoutButton(onClick = onLogoutClick)
        }
    }
}

@Composable
fun ProfileCard(
    username: String,
    email: String,
    avatarEmoji: String,
    launchesFollowed: Int,
    checkInsCount: Int,
    badgesUnlocked: Int,
    onAvatarClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(LiftoffPrimary, CircleShape)
                        .clickable { onAvatarClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = avatarEmoji, fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = username,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = email,
                        fontSize = 13.sp,
                        color = LiftoffTextSecondary
                    )
                    Text(
                        text = "Tap avatar to change",
                        fontSize = 11.sp,
                        color = LiftoffPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(value = launchesFollowed.toString(), label = "Launches\nFollowed", color = LiftoffPrimary)
                ProfileStat(value = checkInsCount.toString(), label = "Check-ins", color = LiftoffPrimary)
                ProfileStat(value = badgesUnlocked.toString(), label = "Badges\nUnlocked", color = LiftoffGold)
            }
        }
    }
}

@Composable
fun ProfileStat(value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = LiftoffTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProfileMenu(
    onSpaceDiaryClick: () -> Unit,
    onAchievementsClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ProfileMenuItem(
            icon = Icons.Outlined.Book,
            label = "Space Diary",
            iconTint = LiftoffPrimary,
            onClick = onSpaceDiaryClick
        )
        ProfileMenuItem(
            icon = Icons.Outlined.MilitaryTech,
            label = "Achievements",
            iconTint = LiftoffGold,
            onClick = onAchievementsClick
        )
        ProfileMenuItem(
            icon = Icons.Outlined.Settings,
            label = "Settings",
            iconTint = LiftoffTextSecondary,
            onClick = onSettingsClick
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    label: String,
    iconTint: Color,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = LiftoffTextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun AboutCard() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "About Liftoff",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Track space launches from agencies around the world. Add launches to your diary, earn badges, and join a community of space enthusiasts!",
                fontSize = 13.sp,
                color = LiftoffTextSecondary,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "Privacy Policy", fontSize = 12.sp, color = LiftoffTextSecondary)
                Text(text = "•", fontSize = 12.sp, color = LiftoffTextSecondary)
                Text(text = "Terms of Service", fontSize = 12.sp, color = LiftoffTextSecondary)
                Text(text = "•", fontSize = 12.sp, color = LiftoffTextSecondary)
                Text(text = "Help", fontSize = 12.sp, color = LiftoffTextSecondary)
            }
        }
    }
}

@Composable
fun LogoutButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, LiftoffError),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = LiftoffError.copy(alpha = 0.1f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.Logout,
            contentDescription = null,
            tint = LiftoffError,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Logout",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = LiftoffError
        )
    }
}

@Composable
fun AvatarPickerDialog(
    onDismiss: () -> Unit,
    onAvatarSelected: (String) -> Unit
) {
    val avatars = listOf(
        "🚀", "👨‍🚀", "👩‍🚀", "🌙", "⭐", "🛸",
        "🌍", "🔭", "🪐", "☄️", "🌟", "🛰️"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = LiftoffSurface,
        title = {
            Text(
                text = "Choose your avatar",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(avatars) { emoji ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(48.dp)
                            .background(LiftoffSurfaceVariant, CircleShape)
                            .clickable { onAvatarSelected(emoji) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = emoji, fontSize = 24.sp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = LiftoffTextSecondary)
            }
        }
    )
}