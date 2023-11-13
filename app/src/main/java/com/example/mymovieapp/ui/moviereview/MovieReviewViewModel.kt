package com.example.mymovieapp.ui.moviereview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.network.responses.movieDetails.ResultUser
import com.example.mymovieapp.repositories.MoviesRepository
import com.example.mymovieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieReviewViewModel : ViewModel() {
    private val repository: MoviesRepository = MoviesRepositoryImpl
    private val movieReviewStateFlow: MutableStateFlow<UIStateMovieReview> = MutableStateFlow(UIStateMovieReview.Loading)

    val movieReviewStateFlowPublic = movieReviewStateFlow.asStateFlow()

    fun getUserReviews(movieId: Int) {
        movieReviewStateFlow.value = UIStateMovieReview.Loading

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getUserReview(movieId)
                }
                movieReviewStateFlow.value = UIStateMovieReview.Success(response.results)
            } catch (e: Exception) {
                movieReviewStateFlow.value = UIStateMovieReview.Error(e)
            }
        }
    }
}

sealed class UIStateMovieReview{
    object Loading : UIStateMovieReview()
    class Error(val e: Exception) : UIStateMovieReview()
    class Success(val movieReviews: List<ResultUser>) : UIStateMovieReview()
}


