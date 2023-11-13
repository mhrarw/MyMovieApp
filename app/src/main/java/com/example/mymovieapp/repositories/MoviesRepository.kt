package com.example.mymovieapp.repositories

import com.example.mymovieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.mymovieapp.network.responses.movieDetails.MovieVideo
import com.example.mymovieapp.network.responses.movieDetails.UserReview
import com.example.mymovieapp.network.responses.movies.MoviesResponse

interface MoviesRepository {

    suspend fun getPopularMovies(page:Int) : MoviesResponse

    suspend fun getMovieDetails(movieId : Int) : MovieDetailsResponse

    suspend fun getUserReview(movieId: Int) : UserReview

    suspend fun getVideoTrailer(movieId: Int): MovieVideo

}