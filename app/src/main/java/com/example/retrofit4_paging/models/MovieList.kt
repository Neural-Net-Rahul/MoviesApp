package com.example.moviesapp.movieList.data.remote.respond

import com.example.retrofit4_paging.models.ResultM

data class MovieList(
    val page: Int ,
    val results: List<ResultM> ,
    val total_pages: Int ,
    val total_results: Int
)