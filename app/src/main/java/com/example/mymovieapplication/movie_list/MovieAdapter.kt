package com.example.mymovieapplication.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapplication.R
import com.example.mymovieapplication.domain.Movie
import com.example.mymovieapplication.repository.WatchlistRepository

class MovieAdapter(private var movieList: List<Movie>) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
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

    object MovieItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem

    }
}
