package com.nvnrdhn.bajps3.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.TMDBApiService
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
    fun streamFavoriteMovie() {
        runBlocking {
            val res = viewModel.streamFavoriteMovie()
            assertNotNull(res)
        }
    }

    @Test
    fun streamFavoriteTv() {
        runBlocking {
            val res = viewModel.streamFavoriteTv()
            assertNotNull(res)
        }
    }
}