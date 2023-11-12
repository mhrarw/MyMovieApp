package com.example.mymovieapp.network.responses.movieDetails


import com.google.gson.annotations.SerializedName

data class MovieVideo(
    val id: Int,
    val results: List<Results>
)

data class Results(
    val id: String,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val key: String, // video url
    val name: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String // id video
)