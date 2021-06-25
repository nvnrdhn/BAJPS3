package com.nvnrdhn.bajps3.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.room.FavoriteDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var apiService: TMDBApiService

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    @Mock
    private lateinit var favoriteListObserver: Observer<List<Favorite>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = MainRepository(apiService, favoriteDao)
        viewModel = FavoriteViewModel(mainRepository)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun getFavoriteMovies() {
        runBlocking {
            whenever(favoriteDao.getAllMovies()).thenReturn(dummyFavoriteList())
            val res = viewModel.getFavoriteMovies()
            verify(favoriteDao).getAllMovies()
            assertNotNull(res)
            assertNotNull(res.value)
            res.observeForever(favoriteListObserver)
            verify(favoriteListObserver).onChanged(dummyFavoriteList())
        }
    }

    @Test
    fun getFavoriteTvShows() {
        runBlocking {
            whenever(favoriteDao.getAllTvShows()).thenReturn(dummyFavoriteList())
            val res = viewModel.getFavoriteTvShows()
            verify(favoriteDao).getAllTvShows()
            assertNotNull(res)
            assertNotNull(res.value)
            res.observeForever(favoriteListObserver)
            verify(favoriteListObserver).onChanged(dummyFavoriteList())
        }
    }

    private fun dummyFavoriteList() = listOf(
        Favorite(1, 1, "", "Movie", "", ""),
        Favorite(2, 2, "", "Movie", "", "")
    )
}