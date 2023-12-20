package com.example.mymovieapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.MovieRepository

/**
 * Shared view model for the parent activity hosting both the Movie List and Movie Details fragments
 */
class SharedViewModel : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> get() = _movieList

    // Initialize the ViewModel and fetch data
    init {
        loadMovies()
    }

    // Data loading method
    private fun loadMovies() {
        _movieList.value = MovieRepository.getInstance().getAllItems()
    }

    public fun onMovieClicked(movie: Movie) {
        TODO("not implemented yet but clicked on movie ${movie}")
    }
}

