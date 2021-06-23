package com.nvnrdhn.bajps3.room

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite WHERE type = 1")
    suspend fun getAllMovies(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE type = 2")
    suspend fun getAllTvShows(): List<Favorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite): Long

    @Delete
    suspend fun deleteFavorite(favorite: Favorite): Int

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun findFavoriteById(id: Int): List<Favorite>
}