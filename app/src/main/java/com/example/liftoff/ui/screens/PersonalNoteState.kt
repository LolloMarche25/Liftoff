package com.example.liftoff.ui.screens

import android.net.Uri
import com.example.liftoff.model.Badge

data class PersonalNoteState(
    val note: String = "",
    val photoUri: Uri? = null,
    val newlyUnlockedBadges: List<Badge> = emptyList(),
    val currentBadgeIndex: Int = 0,
    val isPosted: Boolean = false
)
