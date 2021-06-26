package com.nvnrdhn.bajps3.ui.movies

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.MoviePagingSource
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
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
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    private lateinit var viewModel: MoviesViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var pagingSource: MoviePagingSource

    @Mock
    private lateinit var apiService: TMDBApiService

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pagingSource = MoviePagingSource(apiService)
        mainRepository = MainRepository(apiService, favoriteDao)
        viewModel = MoviesViewModel(mainRepository)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun streamMovieList() {
        runBlocking {
            val res = viewModel.streamMovieList()
            assertNotNull(res)
        }
    }

    @Test
    fun fetchConfig() {
        runBlocking {
            whenever(apiService.getConfig(BuildConfig.API_KEY)).thenReturn(dummyConfigResponse())
            val res = viewModel.fetchConfig()
            verify(apiService).getConfig(BuildConfig.API_KEY)
            assertNotNull(res)
        }
    }

    private fun dummyConfigResponse() = Response.success(ConfigurationResponse())
}