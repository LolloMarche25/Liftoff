package com.example.liftoff.ui.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.liftoff.model.Badge
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.LiftoffBackground
import com.example.liftoff.ui.theme.LiftoffError
import com.example.liftoff.ui.theme.LiftoffGold
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurface
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary
import com.example.liftoff.ui.utils.rememberCameraLauncher

@Composable
fun PersonalNoteScreen(
    navController: NavHostController,
    launchName: String,
    note: String,
    photoUri: Uri?,
    isPosted: Boolean,
    newlyUnlockedBadges: List<Badge>,
    currentBadgeIndex: Int,
    onNoteChange: (String) -> Unit,
    onPhotoTaken: (Uri) -> Unit,
    onPostCheckIn: () -> Unit,
    onDismissBadgeDialog: () -> Unit
) {
    if (newlyUnlockedBadges.isNotEmpty() && currentBadgeIndex < newlyUnlockedBadges.size) {
        val badge = newlyUnlockedBadges[currentBadgeIndex]
        AlertDialog(
            onDismissRequest = { onDismissBadgeDialog() },
            containerColor = LiftoffSurface,
            title = {
                Text(
                    text = "Badge unlocked! \uD83C\uDF89",
                    fontWeight = FontWeight.Bold,
                    color = LiftoffGold
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = badge.emoji, fontSize = 56.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = badge.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = badge.description,
                        fontSize = 13.sp,
                        color = LiftoffTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { onDismissBadgeDialog() },
                    colors = ButtonDefaults.buttonColors(containerColor = LiftoffPrimary)
                ) {
                    Text(text = "Awesome!", color = Color.White)
                }
            }
        )
    }

    val context = LocalContext.current

    val (_, takePicture) = rememberCameraLauncher(
        onPictureTaken = { uri -> onPhotoTaken(uri) }
    )

    Scaffold(
        containerColor = LiftoffBackground,
        topBar = {
            LiftoffTopBar(
                title = "Add to Diary",
                showBackButton = true,
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = launchName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            if (photoUri != null) {
                AsyncImage(
                    model = photoUri,
                    contentDescription = "Foto personale",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(LiftoffSurface, RoundedCornerShape(16.dp))
                        .border(1.dp, LiftoffSurfaceVariant, RoundedCornerShape(16.dp))
                        .clickable { takePicture() },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.AddAPhoto,
                            contentDescription = "Aggiungi foto",
                            tint = LiftoffPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Add a photo",
                            fontSize = 14.sp,
                            color = LiftoffTextSecondary
                        )
                    }
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How do you feel?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = note,
                        onValueChange = onNoteChange,
                        placeholder = {
                            Text(
                                text = "Share your thoughts about this launch...",
                                color = LiftoffTextSecondary,
                                fontSize = 17.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 5
                    )
                    Text(
                        text = "${note.length}/280",
                        fontSize = 11.sp,
                        color = if (note.length >= 260) LiftoffError else LiftoffTextSecondary,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MilitaryTech,
                        contentDescription = null,
                        tint = LiftoffGold,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Earn Rewards!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = LiftoffGold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "By adding launches to your space diary, you'll " +
                                    "earn points and unlock exclusive badges. " +
                                    "Keep tracking launches to level up!",
                            fontSize = 14.sp,
                            color = LiftoffTextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Button(
                onClick = {
                    android.widget.Toast.makeText(
                        context,
                        "Launch added! +100 points",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    onPostCheckIn()
                },
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LiftoffPrimary),
                contentPadding = PaddingValues(vertical = 14.dp),
                modifier = Modifier.fillMaxWidth(),
                enabled = !isPosted
            ) {
                Text(
                    text = if (isPosted) "Added! ✓" else "Add Launch 🚀",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
