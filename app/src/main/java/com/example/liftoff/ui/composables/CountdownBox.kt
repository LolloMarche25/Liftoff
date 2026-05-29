package com.example.liftoff.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liftoff.ui.theme.LiftoffPrimary
import com.example.liftoff.ui.theme.LiftoffSurfaceVariant
import com.example.liftoff.ui.theme.LiftoffTextSecondary

@Composable
fun CountdownBox(value: Int, label: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(LiftoffSurfaceVariant, RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = LiftoffPrimary
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = LiftoffTextSecondary
        )
    }
}