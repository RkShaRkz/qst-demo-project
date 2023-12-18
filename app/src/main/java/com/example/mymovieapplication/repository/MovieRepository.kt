package com.example.mymovieapplication.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mymovieapplication.domain.DataSource
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.domain.Repository
import com.example.mymovieapplication.utils.toImmutableList
import java.time.LocalDate

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

    /**
     * Method for prepopulating the [Repository] with data from the task assignment details file
     */
    private fun populateRepository() {
        val tenet = Movie(
            "Tenet",
            """Armed with only one word, Tenet, and fighting for the survival of the entire world, a
Protagonist journeys through a twilight world of international espionage on a mission that will
unfold in something beyond real time.""",
            7.8f,
            "2h 30min",
            "Action, Sci-Fi",
            createLocalDate(2020, 9, 3),
            "https://www.youtube.com/watch?v=LdOM0x0XDMo"
        )

        val spiderman = Movie(
            "Spider-Man: Into the Spider-Verse",
            """Teen Miles Morales becomes the Spider-Man of his universe, and must join with five
spider-powered individuals from other dimensions to stop a threat for all realities.""",
            8.4f,
            "1h 57min",
            "Action, Animation, Adventure",
            createLocalDate(2018, 12, 14),
            "https://www.youtube.com/watch?v=tg52up16eq0"
        )

        val knivesOut = Movie(
            "Knives Out",
            """A detective investigates the death of a patriarch of an eccentric, combative family.""",
            7.9f,
            "2h 10min",
            "Comedy, Crime, Drama",
            createLocalDate(2019, 11, 27),
            "https://www.youtube.com/watch?v=qGqiHJTsRkQ"
        )

        val guardiansOfTheGalaxy = Movie(
            "Guardians of the Galaxy",
            """A group of intergalactic criminals must pull together to stop a fanatical warrior with
plans to purge the universe.""",
            8.0f,
            "2h 1min",
            "Action, Adventure, Comedy",
            createLocalDate(2014, 8, 1),
            "https://www.youtube.com/watch?v=d96cjJhvlMA"
        )

        val avengers = Movie(
            "Avengers: Age of Ultron",
            """When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping
program called Ultron, things go horribly wrong and it's up to Earth's mightiest heroes to stop the
villainous Ultron from enacting his terrible plan.""",
            7.3f,
            "2h 21min",
            "Action, Adventure, Sci-Fi",
            createLocalDate(2015, 5, 1),
            "https://www.youtube.com/watch?v=tmeOjFno6Do"
        )

        // now that we have all these movies instantiated, we can save them to the datasource
        addItem(tenet)
        addItem(spiderman)
        addItem(knivesOut)
        addItem(guardiansOfTheGalaxy)
        addItem(avengers)
    }

    /**
     * Dispatcher helper method for instantiating [LocalDate]s from the arguments, that delegates
     * to either [createLocalDate_postO] for API26+ devices or [createLocalDate_preO] for earlier devices
     */
    private fun createLocalDate(year: Int, month: Int, dayOfMonth: Int): LocalDate {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createLocalDate_postO(year, month, dayOfMonth)
        } else {
            createLocalDate_preO(year, month, dayOfMonth)
        }
    }

    /**
     * API26+ version of [createLocalDate]
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLocalDate_postO(year: Int, month: Int, dayOfMonth: Int): LocalDate {
        // For API26+
        return LocalDate.of(year, month, dayOfMonth)
    }

    /**
     * API 16 to 25 (inclusive) version of [createLocalDate]
     */
    private fun createLocalDate_preO(year: Int, month: Int, dayOfMonth: Int): LocalDate {
        // Kind of a hack but this method works fine for Pre-Oreo devices
        // For API 16 to 25, inclusive
        return LocalDate.parse("${year}-${month}-${dayOfMonth}")
    }

    companion object {
        @JvmStatic
        lateinit var instance: MovieRepository

        @JvmStatic
        @Synchronized
        public fun getInstance(): MovieRepository {
            synchronized(this) {
                if (::instance.isInitialized.not()) {
                    instance = MovieRepository()
                    instance.populateRepository()
                }

                return instance
            }
        }
    }
}
