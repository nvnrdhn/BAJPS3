package com.nvnrdhn.bajps3.di

import com.nvnrdhn.bajps3.BuildConfig
import com.nvnrdhn.bajps3.data.MainRepository
import com.nvnrdhn.bajps3.data.TMDBApiService
import com.nvnrdhn.bajps3.data.model.ConfigurationResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): TMDBApiService = retrofit.create(TMDBApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService: TMDBApiService) = MainRepository(apiService)
}