package com.nvnrdhn.bajps3.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.room.Favorite
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repo: MainRepository) : ViewModel() {

    fun streamFavoriteTv(): LiveData<PagingData<Favorite>> = repo.fetchFavoriteTv().cachedIn(viewModelScope)

    fun streamFavoriteMovie(): LiveData<PagingData<Favorite>> = repo.fetchFavoriteMovie().cachedIn(viewModelScope)
}