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

    private val _navigationStream = MutableLiveData<NavigationInfo>()
    val navigationStream: LiveData<NavigationInfo> = _navigationStream

    private var navigationState: NavigationState = NavigationState.MOVIE_LIST
    fun getNavigationState(): NavigationState {
        return navigationState
    }

    fun setNavigationState(newState: NavigationState) {
        navigationState = newState
    }

    // Initialize the ViewModel and fetch data
    init {
        navigationState = NavigationState.MOVIE_LIST
        _navigationStream.postValue(NavigationInfo(NavigationState.MOVIE_LIST, false, null))
    }

    public fun onMovieClicked(movie: Movie) {
        navigationState = NavigationState.MOVIE_DETAILS
        _navigationStream.postValue(NavigationInfo(NavigationState.MOVIE_DETAILS, false, movie))
    }

    public fun onBackPressed() {
        navigationState = NavigationState.MOVIE_LIST
        _navigationStream.postValue(NavigationInfo(NavigationState.MOVIE_LIST, true, null))
    }

    public enum class NavigationState { MOVIE_LIST, MOVIE_DETAILS }
    data class NavigationInfo(
        val navigationState: NavigationState,
        val isBackPressed: Boolean,
        val navigationExtraData: Movie?
    )
}

