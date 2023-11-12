package com.example.mymovieapp.ui.moviedetails

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymovieapp.databinding.FragmentMovieDetailsBinding
import com.example.mymovieapp.ui.moviecredits.MovieReviewAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var movieReviewAdapter: MovieReviewAdapter
    private val args : MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieDetailsViewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        val viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]

        movieDetailsViewModel.movieDetailsStateFlowPublic.onEach {
            when(it){
                is UIStateMovieDetails.Loading ->{
                    //
                }
                is UIStateMovieDetails.Success ->{
                    Glide.with(requireContext()).load(it.movieDetails.movieBackground).into(binding.movieImageView)
                    binding.titleTextView.text = it.movieDetails.movieTitle
                    binding.releaseDateTextView.text = "Year Released: " + it.movieDetails.releaseDate.split('-')[0]
                    binding.runtimeTextView.text = "Length: " + (it.movieDetails.runtime / 100).toString() + "h " + (it.movieDetails.runtime % 100).toString() + "min"
                    binding.voteAverageTextView.text = "Rating: " + it.movieDetails.voteAverage.toString()
                    binding.voteCountTextView.text = it.movieDetails.voteCount.toString() + " Votes"
                    binding.overviewTextView.text = it.movieDetails.overview
                }
                is UIStateMovieDetails.Error ->{
                    //
                    Toast.makeText(requireContext(),it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)


        movieDetailsViewModel.getPopularMovies(args.movieId)

        val movieId = arguments?.getInt("movieId") ?: 0
        viewModel.videoTrailer.observe(viewLifecycleOwner, Observer { videoTrailer ->
            val trailerKey = videoTrailer.results.firstOrNull()?.key
            if (!trailerKey.isNullOrBlank()) {
                openYouTube(trailerKey)
            } else {
                Toast.makeText(requireContext(), "No trailer available", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })
            binding.btnPlay.setOnClickListener {
                viewModel.fetchVideoTrailer(movieId)
            }
    }

    private fun openYouTube(trailerKey: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=$trailerKey"))
        startActivity(intent)
    }



}