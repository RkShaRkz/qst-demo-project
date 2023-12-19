package com.example.mymovieapplication.repository

import com.example.mymovieapplication.R
import com.example.mymovieapplication.domain.DataSource
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.domain.Repository
import com.example.mymovieapplication.utils.createLocalDate
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

    override fun deleteItem(item: Movie) {
        TODO("Not yet implemented - no need to, this class won't ever use this")
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
            R.drawable.tenet,
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
            R.drawable.spider_man,
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
            R.drawable.knives_out,
            "Knives Out",
            """A detective investigates the death of a patriarch of an eccentric, combative family.""",
            7.9f,
            "2h 10min",
            "Comedy, Crime, Drama",
            createLocalDate(2019, 11, 27),
            "https://www.youtube.com/watch?v=qGqiHJTsRkQ"
        )

        val guardiansOfTheGalaxy = Movie(
            R.drawable.guardians_of_the_galaxy,
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
            R.drawable.avengers,
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


    companion object {
        @JvmStatic
        private lateinit var _instance: MovieRepository

        @JvmStatic
        @Synchronized
        public fun getInstance(): MovieRepository {
            synchronized(this) {
                if (::_instance.isInitialized.not()) {
                    _instance = MovieRepository()
                    _instance.populateRepository()
                }

                return _instance
            }
        }
    }
}
