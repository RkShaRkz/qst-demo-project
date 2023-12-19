package com.example.mymovieapplication

import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.MovieRepository
import com.example.mymovieapplication.utils.createLocalDate
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MovieRepositoryTest {



    @Test
    fun when_movierepo_is_constructed_then_it_contains_all_expected_movies() {
        val movieRepoItems = MovieRepository.getInstance().getAllItems()
        val listsEqual = movieRepoItems.equals(createMovieRepoInternalMovieList())
        assertTrue(listsEqual)
    }
}