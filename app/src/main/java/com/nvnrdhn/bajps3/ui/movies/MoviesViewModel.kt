package com.nvnrdhn.bajps3.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MoviesViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    fun streamMovieList(): LiveData<PagingData<MovieListItem>> = repo.fetchMovieList().cachedIn(viewModelScope)

    suspend fun fetchConfig(): ConfigurationResponse? = repo.fetchConfig()
}