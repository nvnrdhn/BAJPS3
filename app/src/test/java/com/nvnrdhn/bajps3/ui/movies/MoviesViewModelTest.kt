package com.nvnrdhn.bajps3.ui.movies

import com.nhaarman.mockitokotlin2.whenever
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    @get:Rule
    var rule = MockitoJUnit.rule()

    @Mock
    private lateinit var viewModel: MoviesViewModel

    @Mock
    private lateinit var mainRepository: MainRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
    }

    @Test
    fun streamMovieList() {
    }

    @Test
    fun fetchConfig() {
        runBlocking {
            val mockResponse = mock(ConfigurationResponse::class.java)
            // TODO: masih salah
            `when`(mainRepository.fetchConfig()).thenReturn(mockResponse)
        }
    }
}