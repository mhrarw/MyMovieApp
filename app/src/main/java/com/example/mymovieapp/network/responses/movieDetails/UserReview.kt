package com.example.mymovieapp.network.responses.movieDetails


import com.google.gson.annotations.SerializedName

data class UserReview(
    val id: Int,
    val page: Int,
    val results: List<ResultUser>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class ResultUser(
    val author: String, //name
    val content: String, //reviews
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)