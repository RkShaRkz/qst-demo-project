package com.example.mymovieapplication.domain

import java.net.URL
import java.time.LocalDate

/**
 * Domain class representing a movie with all of it's details
 */
data class Movie(
    val title: String, 
    val description: String, 
    val rating: Float,
    val duration: String,
    val genre: String,
    val releaseDate: LocalDate,
    val trailerLink: String
) {
    val trailerURL: URL = URL(trailerLink)
}