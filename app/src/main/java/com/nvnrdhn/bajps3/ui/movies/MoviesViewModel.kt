package com.nvnrdhn.bajps3.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.MovieListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val _movieList = MutableLiveData<PagingData<MovieListItem>>().apply {
        value = PagingData.empty()
    }
    val movieList: LiveData<PagingData<MovieListItem>> = _movieList

//    fun fetchMovieList() {
//        _movieList.value = repo.fetchMovieList().value ?: PagingData.empty()
//    }

    fun streamMovieList(): LiveData<PagingData<MovieListItem>> = repo.fetchMovieList().cachedIn(viewModelScope)
}