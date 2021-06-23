package com.nvnrdhn.bajps3.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.room.AppDatabase
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.room.FavoriteDao
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: TMDBApiService,
    private val favoriteDao: FavoriteDao) {

    companion object {
        const val NETWORK_PAGE_SIZE = 25
    }

    fun fetchMovieList() = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MoviePagingSource(apiService) }
    ).liveData

    fun fetchTvList() = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { TvPagingSource(apiService) }
    ).liveData

    suspend fun fetchConfig(): ConfigurationResponse? {
        return try {
            apiService.getConfig(BuildConfig.API_KEY).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchMovieDetails(id: Int): MovieDetailResponse? {
        return try {
            apiService.getMovieDetails(id, BuildConfig.API_KEY).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchTvDetails(id: Int): TvDetailResponse? {
        return try {
            apiService.getTvDetails(id, BuildConfig.API_KEY).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchFavoriteMovie(): List<Favorite>? {
        return try {
            favoriteDao.getAllMovies()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchFavoriteTv(): List<Favorite>? {
        return try {
            favoriteDao.getAllTvShows()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun checkFavorite(id: Int): Favorite? {
        return try {
            val fav = favoriteDao.findFavoriteById(id)
            Log.d("checkFavorite", "fav = $fav")
            fav[0]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun addFavorite(fav: Favorite?): Long {
        return if (fav != null) favoriteDao.addFavorite(fav)
        else -1
    }

    suspend fun deleteFavorite(fav: Favorite?): Int {
        return if (fav != null) favoriteDao.deleteFavorite(fav)
        else -1
    }
}