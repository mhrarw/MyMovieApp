package com.example.mymovieapp.models

data class MovieDetails(
    val movieBackground: String,
    val movieTitle: String,
    val releaseDate: String,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
)