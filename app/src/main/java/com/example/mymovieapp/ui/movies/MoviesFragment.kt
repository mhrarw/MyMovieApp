package com.example.mymovieapp.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymovieapp.databinding.FragmentMoviesBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var popularMoviesListAdapter: PopularMoviesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        moviesViewModel.moviesStateFlowPublic.onEach {
            when (it) {
                is UIStateMovies.Loading -> {
                    Toast.makeText(requireContext(), "Data is loading", Toast.LENGTH_LONG).show()
                }
                is UIStateMovies.Success -> {
                    popularMoviesListAdapter.popularMoviesList.clear()
                    popularMoviesListAdapter.popularMoviesList.addAll(it.movies)
                    popularMoviesListAdapter.notifyDataSetChanged()
                }
                is UIStateMovies.Error -> {
                    Toast.makeText(requireContext(), it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        popularMoviesListAdapter = PopularMoviesListAdapter(requireContext()){
            val movieId = it
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieId)
            findNavController().navigate(action)
        }

        binding.popularMoviesRecyclerView.adapter = popularMoviesListAdapter
        moviesViewModel.getPopularMovies()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.popularMoviesRecyclerView.layoutManager = gridLayoutManager

        }

    }

