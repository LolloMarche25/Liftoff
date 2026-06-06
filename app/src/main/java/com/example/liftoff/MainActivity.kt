package com.example.liftoff

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.liftoff.navigation.NavGraph
import com.example.liftoff.ui.theme.LiftoffTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftoffTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}