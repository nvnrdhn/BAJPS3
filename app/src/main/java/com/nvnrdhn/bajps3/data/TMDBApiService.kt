package com.nvnrdhn.bajps3.data

import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import com.nvnrdhn.bajps3.data.model.MovieListResponse
import com.nvnrdhn.bajps3.data.model.TvListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<MovieListResponse>

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Query("api_key") apiKey: String,
        @Path("id") id: Int
    )

    @GET("tv/popular")
    suspend fun getTvList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ) : Response<TvListResponse>

    @GET("tv/{id}")
    suspend fun getTvDetails(
        @Query("api_key") apiKey: String,
        @Path("id") id: Int
    )

    @GET("configuration")
    suspend fun getConfig(
        @Query("api_key") apiKey: String
    ) : Response<ConfigurationResponse>
}