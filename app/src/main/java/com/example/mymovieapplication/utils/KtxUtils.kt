package com.example.mymovieapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Collections

fun <T> List<T>.toImmutableList(): List<T> {
    return Collections.unmodifiableList(this)
}

fun <T> Set<T>.toImmutableSet(): Set<T> {
    return Collections.unmodifiableSet(this)
}

/**
 * Dispatcher helper method for instantiating [LocalDate]s from the arguments, that delegates
 * to either [createLocalDate_postO] for API26+ devices or [createLocalDate_preO] for earlier devices
 */
public fun createLocalDate(year: Int, month: Int, dayOfMonth: Int): LocalDate {
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

    // The 'dumb' 0+month or just month is so that 2020-9-5 can actually be parsed
    // because 2020-9-5 throws an exception but 2020-09-05 works as expected.

    val monthString = if (month < 10) {
        "0${month}"
    } else {
        "${month}"
    }

    val dayString = if (dayOfMonth < 10) {
        "0${dayOfMonth}"
    } else {
        "${dayOfMonth}"
    }

    return LocalDate.parse("${year}-${monthString}-${dayString}")
}