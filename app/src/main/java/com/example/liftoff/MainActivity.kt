package com.example.liftoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.liftoff.model.Launch
import com.example.liftoff.ui.screens.HomeScreen
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
                HomeScreen(fakeLaunch)
            }
        }
    }
}