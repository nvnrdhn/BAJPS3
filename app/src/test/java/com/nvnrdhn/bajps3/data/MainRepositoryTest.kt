package com.nvnrdhn.bajps3.data

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieDetailResponse
import com.nvnrdhn.bajps3.data.model.TvDetailResponse
import com.nvnrdhn.bajps3.room.Favorite
import com.nvnrdhn.bajps3.room.FavoriteDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MainRepositoryTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    private lateinit var apiService: TMDBApiService

    @Mock
    private lateinit var favoriteDao: FavoriteDao

    private lateinit var mainRepository: MainRepository
    private lateinit var moviePagingSource: MoviePagingSource
    private lateinit var tvPagingSource: TvPagingSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        moviePagingSource = MoviePagingSource(apiService)
        tvPagingSource = TvPagingSource(apiService)
        mainRepository = MainRepository(apiService, favoriteDao)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun fetchMovieList() {
        runBlocking {
            val res = mainRepository.fetchMovieList()
            assertNotNull(res)
        }
    }

    @Test
    fun fetchTvList() {
        runBlocking {
            val res = mainRepository.fetchTvList()
            assertNotNull(res)
        }
    }

    @Test
    fun fetchConfig() {
        runBlocking {
            val dummy = dummyConfigResponse()
            whenever(apiService.getConfig(BuildConfig.API_KEY)).thenReturn(dummy)
            val res = mainRepository.fetchConfig()
            verify(apiService).getConfig(BuildConfig.API_KEY)
            assertEquals(dummy.body(), res)
        }
    }

    @Test
    fun fetchMovieDetails() {
        runBlocking {
            val dummy = dummyMovieDetailResponse()
            whenever(apiService.getMovieDetails(550, BuildConfig.API_KEY)).thenReturn(dummy)
            val res = mainRepository.fetchMovieDetails(550)
            verify(apiService).getMovieDetails(550, BuildConfig.API_KEY)
            assertEquals(dummy.body(), res)
        }
    }

    @Test
    fun fetchTvDetails() {
        runBlocking {
            val dummy = dummyTvDetailResponse()
            whenever(apiService.getTvDetails(1399, BuildConfig.API_KEY)).thenReturn(dummy)
            val res = mainRepository.fetchTvDetails(1399)
            verify(apiService).getTvDetails(1399, BuildConfig.API_KEY)
            assertEquals(dummy.body(), res)
        }
    }

    @Test
    fun fetchFavoriteMovie() {
        runBlocking {
            val res = mainRepository.fetchFavoriteMovie()
            assertNotNull(res)
        }
    }

    @Test
    fun fetchFavoriteTv() {
        runBlocking {
            val res = mainRepository.fetchFavoriteTv()
            assertNotNull(res)
        }
    }

    @Test
    fun checkFavorite() {
        runBlocking {
            val dummy = dummyFavoriteList()
            whenever(favoriteDao.findFavoriteById(550)).thenReturn(dummy)
            val res = mainRepository.checkFavorite(550)
            verify(favoriteDao).findFavoriteById(550)
            assertEquals(dummy[0], res)
        }
    }

    @Test
    fun addFavorite() {
        runBlocking {
            val dummy = dummyFavorite()
            whenever(favoriteDao.addFavorite(dummy)).thenReturn(1)
            val res = mainRepository.addFavorite(dummy)
            verify(favoriteDao).addFavorite(dummy)
            assertEquals(1, res)
        }
    }

    @Test
    fun deleteFavorite() {
        runBlocking {
            val dummy = dummyFavorite()
            whenever(favoriteDao.deleteFavorite(dummy)).thenReturn(1)
            val res = mainRepository.deleteFavorite(dummy)
            verify(favoriteDao).deleteFavorite(dummy)
            assertEquals(1, res)
        }
    }

    private fun dummyConfigResponse() = Response.success(ConfigurationResponse())
    private fun dummyMovieDetailResponse() = Response.success(mock(MovieDetailResponse::class.java))
    private fun dummyTvDetailResponse() = Response.success(mock(TvDetailResponse::class.java))
    private fun dummyFavorite() = Favorite(550, 1, "", "", "", "")
    private fun dummyFavoriteList() = listOf(dummyFavorite())
}