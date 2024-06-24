package com.example.retrofit4_paging.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.retrofit4_paging.models.ResultM
import com.example.retrofit4_paging.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(category: String): Flow<PagingData<ResultM>> {
        return movieRepository.getMovies(category).cachedIn(viewModelScope)
    }
    fun searchMovies(text : String) : Flow<PagingData<ResultM>> {
        return movieRepository.searchMovies(text).cachedIn(viewModelScope)
    }
}
