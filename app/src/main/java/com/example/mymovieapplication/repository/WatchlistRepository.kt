package com.example.mymovieapplication.repository

import android.content.Context
import com.example.mymovieapplication.domain.DataSource
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.domain.Repository
import com.example.mymovieapplication.utils.toImmutableList
import com.example.mymovieapplication.utils.toImmutableSet

class WatchlistDataSource(val ctx: Context) : DataSource<Movie> {
    internal val movieSet = mutableSetOf<Movie>()
    private val sharedPref = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    init {
        loadWatchlistFromSharedPref()
    }

    override fun saveItem(item: Movie) {
        movieSet.add(item)
        sharedPref
            .edit()
            .putStringSet(SHARED_PREF_KEY, convertMovieSetToStringSet())
            .commit()
    }

    override fun getItems(): List<Movie> {
        loadWatchlistFromSharedPref()
        // And finally return the persisted watchlist movies
        return movieSet.toList().toImmutableList()
    }

    override fun deleteItem(item: Movie) {
        movieSet.remove(item)
        sharedPref
            .edit()
            .putStringSet(SHARED_PREF_KEY, convertMovieSetToStringSet())
            .commit()
    }

    private fun convertMovieSetToStringSet() : Set<String> {
        // To convert a movie set to a string set, we'll just iterate through
        // all of the movies, and write down their hashcodes as strings
        // then persist that in sharedpref
        val stringSet = mutableSetOf<String>()
        for ( movie in movieSet) {
            stringSet.add(movie.hashCode().toString())
        }

        return stringSet.toImmutableSet()
    }

    private fun convertStringSetToMovieSet(stringSet: Set<String>) : Set<Movie> {
        //TODO fix this since it's hideously suboptimal
        // To convert a string set to a movie set, we'll grab all movies from [MovieRepository]
        // then we'll iterate through that movie set, and add that movie to the resulting set
        // if it's stringified hashcode is already contained in the string set.
        val retVal = mutableSetOf<Movie>()
        val allMovieSet = MovieRepository.getInstance().getAllItems()
        for (movie in allMovieSet) {
            if (stringSet.contains(movie.hashCode().toString())) {
                retVal.add(movie)
            }
        }

        return retVal.toImmutableSet()
    }

    /**
     * Loads the persisted String Set from shared pref then converts it into a Movie set
     * and updates [movieSet]
     */
    @Synchronized
    private fun loadWatchlistFromSharedPref() {
        // Load the persisted watchlist
        val stringSet = sharedPref.getStringSet(SHARED_PREF_KEY, mutableSetOf()) ?: mutableSetOf()
        // Replace the current movieSet with the one from SharedPref
        movieSet.removeAll { _ -> true }
        movieSet.addAll(convertStringSetToMovieSet(stringSet))
    }

    companion object {
        const val SHARED_PREF_NAME = "WatchlistSharedPref"
        const val SHARED_PREF_KEY = "WatchlistKey"
    }
}

class WatchlistRepository(private val ctx: Context, private val dataSource: WatchlistDataSource = WatchlistDataSource(ctx)) : Repository<Movie>(dataSource) {

    public fun isMovieOnWatchlist(movie: Movie) : Boolean {
        return dataSource.movieSet.contains(movie)
    }

    companion object {
        @JvmStatic
        lateinit var instance: WatchlistRepository

        @JvmStatic
        @Synchronized
        public fun getInstance(ctx: Context): WatchlistRepository {
            synchronized(this) {
                if (::instance.isInitialized.not()) {
                    instance = WatchlistRepository(ctx)
                }

                return instance
            }
        }
    }
}
