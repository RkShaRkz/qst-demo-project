package com.example.mymovieapplication.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapplication.R
import com.example.mymovieapplication.SharedViewModel
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.WatchlistRepository

class FragmentMovieDetails : Fragment() {

    private lateinit var movieImage: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieRating: TextView
    private lateinit var watchlistButton: Button
    private lateinit var watchTrailerButton: Button
    private lateinit var shortDescription: TextView
    private lateinit var genre: TextView
    private lateinit var releaseDate: TextView

    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        movieImage = view.findViewById(R.id.movie_image)
        movieTitle = view.findViewById(R.id.movie_title)
        movieRating = view.findViewById(R.id.movie_rating)
        watchlistButton = view.findViewById(R.id.watchlist_button)
        watchTrailerButton = view.findViewById(R.id.watch_trailer)
        shortDescription = view.findViewById(R.id.short_description)
        genre = view.findViewById(R.id.genre)
        releaseDate = view.findViewById(R.id.released_date)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        arguments?.let {
            val movie = it.getParcelable<Movie>(MOVIE_EXTRA)

            movie?.let {
                val watchlistRepo = WatchlistRepository.getInstance(requireContext())
                movieImage.setImageResource(it.drawable)
                movieTitle.text = it.title
                movieRating.text = it.rating.toString()
                watchlistButton.text = if (watchlistRepo.isMovieOnWatchlist(movie)) { getString(R.string.remove_from_watchlist) } else { getString(R.string.add_to_watchlist) }

                watchlistButton.setOnClickListener {v ->
                    if (watchlistRepo.isMovieOnWatchlist(it)) {
                        watchlistRepo.removeFromWatchlist(movie)
                        watchlistButton.text = getString(R.string.add_to_watchlist)
                    } else {
                        watchlistRepo.addToWatchlist(movie)
                        watchlistButton.text = getString(R.string.remove_from_watchlist)
                    }
                }

                watchTrailerButton.setOnClickListener { v -> Toast.makeText(requireContext(), "IMPLEMENT ME", Toast.LENGTH_SHORT).show() }

                shortDescription.text = it.description
                genre.text = it.genre
                releaseDate.text = "${it.releaseDate.year}, ${it.releaseDate.dayOfMonth} ${it.releaseDate.month}"
            }
        }

        return view
    }

    companion object {
        const val MOVIE_EXTRA = "movie_extra"
    }
}
