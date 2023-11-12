package com.example.mymovieapp.repositories

import com.example.mymovieapp.network.api.ApiProvider
import com.example.mymovieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.mymovieapp.network.responses.movieDetails.MovieVideo
import com.example.mymovieapp.network.responses.movieDetails.UserReview
import com.example.mymovieapp.network.responses.movies.MoviesResponse

object MoviesRepositoryImpl : MoviesRepository {
    private val apiService = ApiProvider.apiService

    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun getUserReview(movieId: Int): UserReview {
        return apiService.getUserReview(movieId)
    }

    override suspend fun getVideoTrailer(movieId: Int): MovieVideo {
        return apiService.getVideoTrailer(movieId)
    }
}