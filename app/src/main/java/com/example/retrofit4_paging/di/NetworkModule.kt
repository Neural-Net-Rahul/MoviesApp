package com.example.retrofit4_paging.di

import com.example.retrofit4_paging.retrofit.MovieAPI
import com.example.retrofit4_paging.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun getMovieAPI(retrofit : Retrofit):MovieAPI{
        return retrofit.create(MovieAPI::class.java)
    }
}