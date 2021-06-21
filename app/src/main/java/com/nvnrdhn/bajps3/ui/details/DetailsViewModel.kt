package com.nvnrdhn.bajps3.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {
    private val movieDetails = MutableLiveData<MovieDetailResponse?>()
    private val tvDetails = MutableLiveData<TvDetailResponse?>()
    var config: ConfigurationResponse? = null

    fun getMovieDetails(id: Int): LiveData<MovieDetailResponse?> {
        viewModelScope.launch {
            val details = repo.fetchMovieDetails(id)
            movieDetails.value = details
        }
        return movieDetails
    }

    fun getTvDetails(id: Int): LiveData<TvDetailResponse?> {
        viewModelScope.launch {
            val details = repo.fetchTvDetails(id)
            tvDetails.value = details
        }
        return tvDetails
    }

    fun fetchConfig() {
        viewModelScope.launch {
            config = repo.fetchConfig()
        }
    }
}