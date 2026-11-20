package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserPreferenceEntity(
    @PrimaryKey
    val key: String = "current_user",
    val displayName: String = "Guest User",
    val email: String = "guest@local",
    val provider: String = "GUEST",
    val isGuest: Boolean = true,
    val preferredArchitecture: String = "x64 (64-bit)",
    val preferredLanguage: String = "English (United States)",
    val downloadOverWifiOnly: Boolean = true,
    val isProSubscribed: Boolean = false,
    val subscriptionPlan: String = "NONE",
    val proRequestSent: Boolean = false,
    val proRequestEmail: String = "",
    val proRequestRef: String = "",
    val joinedTimestamp: Long = System.currentTimeMillis()
)
