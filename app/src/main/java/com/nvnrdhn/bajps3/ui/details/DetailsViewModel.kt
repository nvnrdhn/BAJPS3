package com.nvnrdhn.bajps3.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.room.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val movieDetails = MutableLiveData<MovieDetailResponse?>()
    private val tvDetails = MutableLiveData<TvDetailResponse?>()
    private val isLoading = MutableLiveData<Boolean>()
    private val isFavorite = MutableLiveData<Favorite?>()
    var config: ConfigurationResponse? = null

    fun fetchMovieDetails(id: Int): LiveData<MovieDetailResponse?> {
        isLoading.value = true
        viewModelScope.launch {
            val details = repo.fetchMovieDetails(id)
            isLoading.value = false
            movieDetails.value = details
        }
        return movieDetails
    }

    fun fetchTvDetails(id: Int): LiveData<TvDetailResponse?> {
        isLoading.value = true
        viewModelScope.launch {
            val details = repo.fetchTvDetails(id)
            isLoading.value = false
            tvDetails.value = details
        }
        return tvDetails
    }

    fun getMovieDetails() = movieDetails.value
    fun getTvDetails() = tvDetails.value

    fun fetchConfig() {
        viewModelScope.launch {
            config = repo.fetchConfig()
        }
    }

    fun checkFavorite(id: Int): LiveData<Favorite?> {
        viewModelScope.launch {
            isFavorite.value = repo.checkFavorite(id)
        }
        return isFavorite
    }

    fun addFavorite(fav: Favorite?) {
        viewModelScope.launch {
            if (fav != null) {
                repo.addFavorite(fav)
                isFavorite.value = repo.checkFavorite(fav.id)
            }
        }
    }

    fun deleteFavorite(fav: Favorite?) {
        viewModelScope.launch {
            if (fav != null) {
                repo.deleteFavorite(fav)
                isFavorite.value = repo.checkFavorite(fav.id)
            }
        }
    }

    fun isLoading(): LiveData<Boolean> = isLoading
}