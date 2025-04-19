package com.example.apilist.data.database

import androidx.room.*
import com.example.apilist.data.model.local.UserSettings

@Dao
interface SettingsDao {

    @Query("SELECT * FROM user_settings WHERE id = 1")
    suspend fun getSettings(): UserSettings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSettings(settings: UserSettings)

    @Query("DELETE FROM user_settings")
    suspend fun clearSettings()
}
