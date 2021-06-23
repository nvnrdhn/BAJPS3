package com.nvnrdhn.bajps3.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "cover_url") val coverUrl: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "description") val description: String?
)