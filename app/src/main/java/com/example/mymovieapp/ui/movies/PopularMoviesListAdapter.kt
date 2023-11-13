package com.example.mymovieapp.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovieapp.R
import com.example.mymovieapp.models.Movie

class PopularMoviesListAdapter(
    var context: Context,
    val click: (Int) -> Unit,
    val loadMore: () -> Unit
) : RecyclerView.Adapter<PopularMoviesListAdapter.ViewHolder>() {

    private val popularMoviesList: MutableList<Movie> = mutableListOf()

    fun setMovies(movies: List<Movie>) {
        popularMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_movies_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(popularMoviesList[position].movieBackground).into(holder.movieImage)
        holder.movieTitle.text = popularMoviesList[position].movieTitle
        holder.releaseDate.text = popularMoviesList[position].releaseDate
        holder.itemView.setOnClickListener {
            click.invoke(popularMoviesList[position].movieId)
        }
        if (position == itemCount - 1) {
            loadMore.invoke()
        }
    }

    override fun getItemCount(): Int {
        return popularMoviesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val movieImage: ImageView = itemView.findViewById(R.id.moviePictureImageView)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitleTextView)
        val releaseDate: TextView = itemView.findViewById(R.id.movie_release_date)
    }
}