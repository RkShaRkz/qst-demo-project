package com.example.mymovieapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.movie_details.FragmentMovieDetails
import com.example.mymovieapplication.movie_list.FragmentMovieList

class ActivityMain : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var sharedViewModel: SharedViewModel

    private var displayState: SharedViewModel.NavigationState =
        SharedViewModel.NavigationState.MOVIE_LIST


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.navigationStream.observe(this, Observer { navigationInfo ->
            Log.d(LOGTAG, "Obtained navigation info: ${navigationInfo}")
            // Update the UI with the new list of movies
            sharedViewModel.setNavigationState(navigationInfo.navigationState)
            when (navigationInfo.navigationState) {
                SharedViewModel.NavigationState.MOVIE_LIST -> transitionToMovieList(navigationInfo.isBackPressed)
                SharedViewModel.NavigationState.MOVIE_DETAILS -> transitionToMovieDetail(
                    navigationInfo.navigationExtraData
                )
            }
        })
    }

    override fun onBackPressed() {
        when (sharedViewModel.getNavigationState()) {
            SharedViewModel.NavigationState.MOVIE_LIST -> super.onBackPressed()
            SharedViewModel.NavigationState.MOVIE_DETAILS -> sharedViewModel.onBackPressed()//transitionToMovieList(true)
        }
    }

    private fun transitionToMovieDetail(movie: Movie?) {
        val fm: FragmentManager = supportFragmentManager

        movie?.let {
            val fragment = FragmentMovieDetails()
            val extras = Bundle()
            extras.putParcelable(FragmentMovieDetails.MOVIE_EXTRA, movie)

            fm.beginTransaction().replace(R.id.fragment_container_view, fragment.javaClass, extras)
                .commitNow()
        }
    }

    private fun transitionToMovieList(isBackPressed: Boolean) {
        val fm: FragmentManager = supportFragmentManager
        if (isBackPressed.not()) {
            val fragment = FragmentMovieList()
            fm
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .addToBackStack(INITIAL_LIST_STATE)
                .commit()
        } else {
            fm.popBackStack(INITIAL_LIST_STATE, 0)
        }
    }

    companion object {
        const val LOGTAG = "ActivityMain"
        const val INITIAL_LIST_STATE = "list_initial"
    }
}
