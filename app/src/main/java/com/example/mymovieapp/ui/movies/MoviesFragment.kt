package com.example.mymovieapp.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
                    popularMoviesListAdapter.setMovies(it.movies)
                }
                is UIStateMovies.Error -> {
                    Toast.makeText(requireContext(), it.e.message, Toast.LENGTH_LONG).show()
                }
            }
        }.launchIn(lifecycleScope)

        popularMoviesListAdapter = PopularMoviesListAdapter(requireContext(),
            click = {
                val movieId = it
                val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieId)
                findNavController().navigate(action)
            },
            loadMore = {
                // Load more items callback
                moviesViewModel.getPopularMovies()
            }
        )

        binding.popularMoviesRecyclerView.adapter = popularMoviesListAdapter
        val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.popularMoviesRecyclerView.layoutManager = gridLayoutManager

        binding.popularMoviesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItem >= totalItemCount && totalItemCount > 0) {
                    // Load more items when reaching the end
                    moviesViewModel.getPopularMovies()
                }
            }
        })
        moviesViewModel.getPopularMovies()
    }
}

