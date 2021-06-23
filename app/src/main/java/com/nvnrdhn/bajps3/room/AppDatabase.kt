package com.nvnrdhn.bajps3.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Favorite::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}