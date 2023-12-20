package com.example.mymovieapplication.movie_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapplication.R
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.WatchlistRepository

class MovieAdapter(private var movieList: List<Movie>) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(Movie.MovieItemDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
//        val movie = movieList[position]
//        holder.bind(movie)
        holder.bind(getItem(position))
    }

//    override fun getItemCount(): Int {
//        super.getItemCount()
//        return movieList.size
//    }

    fun setItems(newMovieList: List<Movie>) {
        movieList = newMovieList
    }

    override fun onCurrentListChanged(
        previousList: MutableList<Movie>,
        currentList: MutableList<Movie>
    ) {
        Log.d(LOGTAG, "previousList = ${previousList}\tcurrentList = ${currentList}\tmovieList = ${movieList}")
        super.onCurrentListChanged(previousList, currentList)
        movieList = currentList
    }

    // ViewHolder class
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.item_image)
        private val title: TextView = itemView.findViewById(R.id.item_title)
        private val details: TextView = itemView.findViewById(R.id.item_details)
        private val watchlistLabel: TextView = itemView.findViewById(R.id.item_watchlist_label)

        fun bind(movie: Movie) {
            image.setImageResource(movie.drawable)
            title.text = movie.title
            details.text = "${movie.duration} - ${movie.genre}"
            watchlistLabel.visibility =
                if (WatchlistRepository.getInstance(itemView.context).isMovieOnWatchlist(movie)) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    companion object {
        private const val LOGTAG = "MovieAdapter"
    }
}
