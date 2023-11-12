package com.example.mymovieapp.ui.moviecredits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.network.responses.movieDetails.ResultUser

class MovieReviewAdapter(private val reviews: List<ResultUser>) :
    RecyclerView.Adapter<MovieReviewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_reviews_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val authorTextView: TextView = itemView.findViewById(R.id.name_user)
        private val contentTextView: TextView = itemView.findViewById(R.id.user_review)

        fun bind(review: ResultUser) {
            authorTextView.text = review.author
            contentTextView.text = review.content
        }
    }
}