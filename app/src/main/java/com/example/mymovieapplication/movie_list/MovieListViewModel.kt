package com.example.mymovieapplication.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.MovieRepository

class MovieListViewModel : ViewModel() {

    // MutableLiveData for holding the list of movies
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
}

