package com.example.mymovieapp.ui.moviecredits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.network.responses.movieDetails.ResultUser
import com.example.mymovieapp.network.responses.movieDetails.UserReview
import com.example.mymovieapp.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieReviewViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _movieReviews = MutableLiveData<List<ResultUser>>()
    val movieReviews: LiveData<List<ResultUser>> get() = _movieReviews


}


