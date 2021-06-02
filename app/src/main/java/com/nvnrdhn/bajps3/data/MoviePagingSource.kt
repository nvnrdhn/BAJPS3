package com.nvnrdhn.bajps3.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.model.MovieListItem

class MoviePagingSource(private val service: TMDBApiService) : PagingSource<Int, MovieListItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, MovieListItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieListItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val res = service.getMovieList(BuildConfig.API_KEY, position)
            val list = res.body()!!.results
            Log.d("PAGING_SOURCE", list.size.toString())
            LoadResult.Page(
                data = list,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (list.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}