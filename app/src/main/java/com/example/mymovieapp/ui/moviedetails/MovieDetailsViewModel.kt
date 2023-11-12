package com.example.mymovieapp.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.models.MovieDetails
import com.example.mymovieapp.network.responses.movieDetails.MovieVideo
import com.example.mymovieapp.repositories.MoviesRepository
import com.example.mymovieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailsViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl
    private val movieDetailsStateFlow: MutableStateFlow<UIStateMovieDetails> =
        MutableStateFlow(UIStateMovieDetails.Loading)
    val movieDetailsStateFlowPublic = movieDetailsStateFlow.asStateFlow()

    private val _videoTrailer = MutableLiveData<MovieVideo>()
    val videoTrailer: LiveData<MovieVideo> get() = _videoTrailer

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getPopularMovies(movieId: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    moviesRepository.getMovieDetails(movieId)
                }
                val partOfUrl = "https://image.tmdb.org/t/p/original"
                val movieDetails = MovieDetails(
                    movieBackground = partOfUrl + response.backdrop_path,
                    movieTitle = response.title,
                    releaseDate = response.release_date,
                    runtime = response.runtime,
                    voteAverage = response.vote_average,
                    voteCount = response.vote_count,
                    overview = response.overview
                )
                movieDetailsStateFlow.value = UIStateMovieDetails.Success(movieDetails)
            } catch (e: Exception) {
                movieDetailsStateFlow.value = UIStateMovieDetails.Error(e)
            }
        }
    }

    fun fetchVideoTrailer(movieId: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val videoTrailer = MoviesRepositoryImpl.getVideoTrailer(movieId)
                _videoTrailer.value = videoTrailer
            } catch (e: Exception) {
                _error.value = "Error fetching trailer: ${e.message}"
            }
        }
    }
}

    sealed class UIStateMovieDetails {
        object Loading : UIStateMovieDetails()
        class Error(val e: Exception) : UIStateMovieDetails()
        class Success(val movieDetails: MovieDetails) : UIStateMovieDetails()
    }

