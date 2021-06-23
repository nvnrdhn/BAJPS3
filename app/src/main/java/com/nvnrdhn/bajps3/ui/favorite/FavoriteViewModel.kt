package com.nvnrdhn.bajps3.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.room.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    private val movies = MutableLiveData<List<Favorite>>()
    private val tvs = MutableLiveData<List<Favorite>>()
    private val loading = MutableLiveData<Boolean>()

    fun getFavoriteMovies(): LiveData<List<Favorite>> {
        loading.value = true
        viewModelScope.launch {
            movies.value = repo.fetchFavoriteMovie()
            loading.value = false
        }
        return movies
    }
    fun getFavoriteTvShows(): LiveData<List<Favorite>> {
        viewModelScope.launch {
            tvs.value = repo.fetchFavoriteTv()
            loading.value = false
        }
        return tvs
    }
    fun isLoading(): LiveData<Boolean> = loading
}