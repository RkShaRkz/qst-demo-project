package com.example.mymovieapplication.repository

import com.example.mymovieapplication.domain.DataSource
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.domain.Repository
import com.example.mymovieapplication.utils.toImmutableList

/**
 * A [DataSource] for [Movie]s
 */
class MovieDataSource : DataSource<Movie> {
    private val movieList = mutableListOf<Movie>()
    override fun saveItem(item: Movie) {
        movieList.add(item)
    }

    override fun getItems(): List<Movie> {
        return movieList.toImmutableList()
    }
}

/**
 * A [Repository] for handling [Movie] items, delegating persistence to an internal
 * implicitly constructed [MovieDataSource]
 */
class MovieRepository(private val dataSource: MovieDataSource = MovieDataSource()) :
    Repository<Movie>(dataSource) {

        companion object {
            @JvmStatic
            lateinit var instance: MovieRepository

            @JvmStatic
            @Synchronized
            public fun getInstance() : MovieRepository {
                synchronized(this) {
                    if (::instance.isInitialized.not()) {
                        instance = MovieRepository()
                    }

                    return instance
                }
            }
        }
}
