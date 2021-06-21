package com.nvnrdhn.bajps3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: TMDBApiService) {

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

    suspend fun fetchMovieDetails(id: Int) = apiService.getMovieDetails(id, BuildConfig.API_KEY).body()

    suspend fun fetchTvDetails(id: Int) = apiService.getTvDetails(id, BuildConfig.API_KEY).body()
}