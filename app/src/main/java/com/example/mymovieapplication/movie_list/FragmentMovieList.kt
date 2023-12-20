package com.example.mymovieapplication.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapplication.R

class FragmentMovieList : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        adapter = MovieAdapter(listOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observe changes in the movieList LiveData
        viewModel.movieList.observe(viewLifecycleOwner, Observer { movies ->
            // Update the UI with the new list of movies
            adapter.submitList(movies)
        })

        return view
    }
}
