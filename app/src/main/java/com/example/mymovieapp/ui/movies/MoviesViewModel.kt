package com.example.mymovieapp.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.repositories.MoviesRepository
import com.example.mymovieapp.repositories.MoviesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel : ViewModel() {
    private val moviesRepository: MoviesRepository = MoviesRepositoryImpl
    private val moviesStateFlow: MutableStateFlow<UIStateMovies> =
        MutableStateFlow(UIStateMovies.Loading)

    private var currentPage = 1
    private var isLoading = false
    private val loadedMovieIds = mutableSetOf<Int>()
    val moviesStateFlowPublic = moviesStateFlow.asStateFlow()


    fun getPopularMovies() {
        if (isLoading) return

        viewModelScope.launch {
            try {
                isLoading = true

                val response = withContext(Dispatchers.IO) {
                    moviesRepository.getPopularMovies()
                }

                val moviesList = mutableListOf<Movie>()
                for (i in response.results.indices) {
                    val movieId = response.results[i].id

                    if (!loadedMovieIds.contains(movieId)) {
                        moviesList.add(
                            Movie(
                                "https://image.tmdb.org/t/p/original" + response.results[i].backdrop_path,
                                response.results[i].title,
                                response.results[i].release_date,
                                movieId
                            )
                        )
                        loadedMovieIds.add(movieId)
                    }
                }

                val existingMovies = (moviesStateFlowPublic.value as? UIStateMovies.Success)?.movies
                    ?: emptyList()

                moviesStateFlow.value =
                    UIStateMovies.Success(existingMovies + moviesList)

                currentPage++
            } catch (e: Exception) {
                moviesStateFlow.value = UIStateMovies.Error(e)
            } finally {
                isLoading = false
            }
        }
    }
}


    sealed class UIStateMovies {
        object Loading : UIStateMovies()
        class Error(val e: Exception) : UIStateMovies()
        class Success(val movies: List<Movie>) : UIStateMovies()
    }
