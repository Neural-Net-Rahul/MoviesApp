package com.example.retrofit4_paging.retrofit

import com.example.moviesapp.movieList.data.remote.respond.MovieList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/{category}")
    suspend fun getMovies(
        @Path("category") category:String ,
        @Query("page") page : Int ,
        @Query("api_key") api_key : String = "3751f894fcc8779f291d956c23184229"
    ): MovieList

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") api_key : String = "3751f894fcc8779f291d956c23184229"
    ): MovieList
}