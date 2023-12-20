package com.example.mymovieapplication.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import com.example.mymovieapplication.utils.parseLocalDateFromParsableString
import com.example.mymovieapplication.utils.toParsableString
import java.net.URL
import java.time.LocalDate

/**
 * Domain class representing a movie with all of it's details
 */
data class Movie(
    @DrawableRes
    val drawable: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val duration: String,
    val genre: String,
    val releaseDate: LocalDate,
    val trailerLink: String
) : Parcelable {
    val trailerURL: URL = URL(trailerLink)


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!.parseLocalDateFromParsableString(),
        parcel.readString()!!
    ) {
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (title != other.title) return false
        if (description != other.description) return false
        if (rating != other.rating) return false
        if (duration != other.duration) return false
        if (genre != other.genre) return false
        if (releaseDate != other.releaseDate) return false
        return trailerURL == other.trailerURL
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + trailerURL.hashCode()
        return result
    }

    object MovieItemDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem.title == oldItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(drawable)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeFloat(rating)
        parcel.writeString(duration)
        parcel.writeString(genre)
        parcel.writeString(releaseDate.toParsableString())
        parcel.writeString(trailerLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }


}
