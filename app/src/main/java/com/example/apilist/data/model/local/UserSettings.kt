package com.example.apilist.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val isDarkTheme: Boolean = false,
    val isTextView: Boolean = true,
    val showManaCost: Boolean = true,
    val showTypeLine: Boolean = true,
    val showOracleText: Boolean = true,
    val showPowerToughness: Boolean = true
)