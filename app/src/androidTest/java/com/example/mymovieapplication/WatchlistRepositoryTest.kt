package com.example.mymovieapplication

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mymovieapplication.repository.MovieRepository
import com.example.mymovieapplication.repository.WatchlistRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class WatchlistRepositoryTest {

    @Test
    fun when_watchlistrepo_is_constructed_then_it_contains_no_movies_on_the_watchlist() {
        val ctx = InstrumentationRegistry.getInstrumentation().context //can use .targetContext too
        val watchlistRepo = WatchlistRepository.getInstance(ctx)

        assertTrue(watchlistRepo.getAllItems().isEmpty())
    }

    @Test
    fun when_movie_is_added_to_watchlistrepo_then_it_is_contained() {
        val movieRepoItems = MovieRepository.getInstance().getAllItems()
        val ctx = InstrumentationRegistry.getInstrumentation().context
        val watchlistRepo = WatchlistRepository.getInstance(ctx)

        val movie = movieRepoItems[0]
        watchlistRepo.addItem(movie)

        assertTrue(watchlistRepo.getAllItems().get(0).equals(movie))
    }
}