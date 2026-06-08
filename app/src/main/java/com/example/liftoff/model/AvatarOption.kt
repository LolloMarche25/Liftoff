package com.example.liftoff.model

data class AvatarOption(
    val emoji: String,
    val requiredCheckIns: Int
)

val avatarOptions = listOf(
    AvatarOption("🚀", 1),
    AvatarOption("\uD83C\uDF16", 3),
    AvatarOption("\uD83D\uDEF8", 5),
    AvatarOption("\uD83D\uDEF0\uFE0F", 7),
    AvatarOption("\uD83E\uDDD1\u200D\uD83D\uDE80", 10),
    AvatarOption("\uD83D\uDD2D", 15)
)