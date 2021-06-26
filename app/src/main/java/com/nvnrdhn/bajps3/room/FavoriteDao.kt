package com.nvnrdhn.bajps3.room

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: Favorite): Long

    @Delete
    suspend fun deleteFavorite(favorite: Favorite): Int

    @Query("SELECT * FROM favorite WHERE id = :id")
    suspend fun findFavoriteById(id: Int): List<Favorite>

    @Query("SELECT * FROM favorite WHERE type = 1")
    fun streamFavoriteMovies(): PagingSource<Int, Favorite>

    @Query("SELECT * FROM favorite WHERE type = 2")
    fun streamFavoriteTv(): PagingSource<Int, Favorite>
}