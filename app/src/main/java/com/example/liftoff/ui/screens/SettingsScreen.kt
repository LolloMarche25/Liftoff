package com.example.liftoff.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.liftoff.ui.composables.LiftoffTopBar
import com.example.liftoff.ui.theme.*

@Composable
fun SettingsScreen(
    navController: NavHostController,
    username: String,
    notificationsEnabled: Boolean,
    onUsernameChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            LiftoffTopBar(
                title = "Settings",
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
            Text(
                text = "Account",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = LiftoffPrimary
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LiftoffSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = onUsernameChange,
                        label = { Text("Username", color = LiftoffTextSecondary) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                                tint = LiftoffPrimary
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = LiftoffPrimary,
                            unfocusedBorderColor = LiftoffSurfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text(
                text = "Preferences",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = LiftoffPrimary
            )

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
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = null,
                        tint = LiftoffPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Notifications",
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                    val context = LocalContext.current
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = {
                            val intent = android.content.Intent(
                                android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
                            ).apply {
                                putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, context.packageName)
                            }
                            context.startActivity(intent)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = LiftoffPrimary
                        )
                    )
                }
            }
        }
    }
}