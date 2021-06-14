package com.nvnrdhn.bajps3.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.TvListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    fun streamTvList(): LiveData<PagingData<TvListItem>> = repo.fetchTvList().cachedIn(viewModelScope)

    suspend fun fetchConfig(): ConfigurationResponse? = repo.fetchConfig()
}