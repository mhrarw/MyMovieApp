package com.example.mymovieapp.network.api

import com.example.mymovieapp.network.responses.movieDetails.MovieDetailsResponse
import com.example.mymovieapp.network.responses.movieDetails.MovieVideo
import com.example.mymovieapp.network.responses.movieDetails.UserReview
import com.example.mymovieapp.network.responses.movies.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("popular?api_key=e34678e6a63a3597405ef2823c23bc57")
    suspend fun getPopularMovies() : MoviesResponse

    @GET("{movie_id}?api_key=e34678e6a63a3597405ef2823c23bc57")
    suspend fun getMovieDetails(@Path("movie_id")movieId : Int) : MovieDetailsResponse

    @GET("{movie_id}/videos?api_key=e34678e6a63a3597405ef2823c23bc57")
    suspend fun getVideoTrailer(@Path("movie_id")movieId : Int) : MovieVideo

    @GET("{movie_id}/reviews?api_key=e34678e6a63a3597405ef2823c23bc57")
    suspend fun getUserReview(@Path("movie_id")movieId : Int) : UserReview

}