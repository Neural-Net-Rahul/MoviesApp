package com.example.retrofit4_paging.Paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.retrofit.MovieAPI

class MoviePagingSource(private val movieAPI : MovieAPI , private val category :String): PagingSource<Int, ResultM>() {

    override fun getRefreshKey(state: PagingState<Int, ResultM>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultM> {
        return try {
            val position = params.key ?: 1
            val response = movieAPI.getMovies(category, position)
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
