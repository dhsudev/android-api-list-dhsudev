package com.example.apilist.data.database

import androidx.room.*
import com.example.apilist.data.model.local.FavoriteCard

@Dao
interface FavoriteCardDao {

    @Query("SELECT * FROM favorite_cards")
    suspend fun getAllFavorites(): List<FavoriteCard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(card: FavoriteCard)

    @Delete
    suspend fun deleteFavorite(card: FavoriteCard)

    @Query("DELETE FROM favorite_cards")
    suspend fun clearFavorites()

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_cards WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
