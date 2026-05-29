package com.example.liftoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.liftoff.model.Launch
import com.example.liftoff.ui.screens.HomeScreen
import com.example.liftoff.ui.screens.LaunchDetailScreen
import com.example.liftoff.ui.theme.LiftoffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftoffTheme {
                val fakeLaunch = Launch(
                    name = "Starlink Group 6-42",
                    rocket = "Falcon 9",
                    agency = "SpaceX",
                    location = "Kennedy Space Center, FL",
                    status = "Scheduled",
                    daysLeft = 2,
                    hoursLeft = 14,
                    minutesLeft = 29,
                    secondsLeft = 58
                )
                val fakeUpcoming = listOf(
                    Launch("Starlink Group 6-42", "Falcon 9", "SpaceX", "Kennedy Space Center, FL", "Scheduled"),
                    Launch("Artemis III", "SLS Block 1B", "NASA", "Kennedy Space Center, FL", "Scheduled")
                )
                //HomeScreen(fakeLaunch, fakeUpcoming)
                LaunchDetailScreen(fakeLaunch)
            }
        }
    }
}