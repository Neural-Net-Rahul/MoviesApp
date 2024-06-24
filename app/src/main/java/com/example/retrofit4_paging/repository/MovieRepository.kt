package com.example.retrofit4_paging.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.retrofit4_paging.Paging.MoviePagingSource
import com.example.retrofit4_paging.Paging.SearchPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.retrofit.MovieAPI

class MovieRepository @Inject constructor(val movieAPI: MovieAPI) {
    fun getMovies(category : String): Flow<PagingData<ResultM>> = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviePagingSource(movieAPI,category) }
    ).flow

    fun searchMovies(text : String): Flow<PagingData<ResultM>> = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { SearchPagingSource(movieAPI,text) }
    ).flow
}