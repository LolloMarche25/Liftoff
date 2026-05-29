package com.example.liftoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.liftoff.model.Launch
import com.example.liftoff.navigation.NavGraph
import com.example.liftoff.ui.theme.LiftoffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftoffTheme {
                val navController = rememberNavController()
                val launches = listOf(
                    Launch(
                        id = 1,
                        name = "Starlink Group 6-42",
                        rocket = "Falcon 9",
                        agency = "SpaceX",
                        location = "Kennedy Space Center, FL",
                        status = "Scheduled",
                        daysLeft = 2,
                        hoursLeft = 14,
                        minutesLeft = 29,
                        secondsLeft = 58,
                        description = "This mission will deploy 60 Starlink satellites."
                    ),
                    Launch(
                        id = 2,
                        name = "Artemis III",
                        rocket = "SLS Block 1B",
                        agency = "NASA",
                        location = "Kennedy Space Center, FL",
                        status = "Scheduled"
                    )
                )
                NavGraph(navController = navController, launches = launches)
            }
        }
    }
}