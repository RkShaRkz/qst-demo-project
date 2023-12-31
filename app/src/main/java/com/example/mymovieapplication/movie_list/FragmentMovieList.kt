package com.example.mymovieapplication.movie_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapplication.R
import com.example.mymovieapplication.SharedViewModel

class FragmentMovieList : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieListViewModel
    private lateinit var activityViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        activityViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        adapter = MovieAdapter(
            listOf(),
            { clickedMovie -> activityViewModel.onMovieClicked(clickedMovie) }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        // Observe changes in the movieList LiveData
        viewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            Log.d(LOGTAG, "Obtained movie list: ${movies}")
            // Update the UI with the new list of movies
            adapter.submitList(movies)
        })
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext()))

        return view
    }

    companion object {
        private const val LOGTAG = "FragmentMovieList"
    }
}
