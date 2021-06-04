package com.nvnrdhn.bajps3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nvnrdhn.bajps3.BuildConfig
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

    suspend fun fetchConfig() = apiService.getConfig(BuildConfig.API_KEY).body()
}