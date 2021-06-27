package com.nvnrdhn.bajps3.ui.movies

import androidx.paging.PagingSource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.MoviePagingSource
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieListResponse
import com.nvnrdhn.bajps3.room.FavoriteDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
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
            whenever(apiService.getMovieList(BuildConfig.API_KEY, 1)).thenReturn(dummyMovieListResponse())
            val res = pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = MainRepository.NETWORK_PAGE_SIZE,
                    placeholdersEnabled = false)
            )
            assertEquals(
                PagingSource.LoadResult.Page(
                    data = dummyMovieListResponse().body()!!.results,
                    prevKey = null,
                    nextKey = null
                ),
                res
            )
        }
    }

    @Test
    fun fetchConfig() {
        runBlocking {
            val dummy = dummyConfigResponse()
            whenever(apiService.getConfig(BuildConfig.API_KEY)).thenReturn(dummy)
            val res = viewModel.fetchConfig()
            verify(apiService).getConfig(BuildConfig.API_KEY)
            assertEquals(dummy.body(), res)
        }
    }

    private fun dummyConfigResponse() = Response.success(ConfigurationResponse())
    private fun dummyMovieListResponse() = Response.success(
        MovieListResponse(
            1,
            1,
            listOf(),
            1
        )
    )
}