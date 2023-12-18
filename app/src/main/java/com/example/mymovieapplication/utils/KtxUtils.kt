package com.example.mymovieapplication.utils

import java.util.Collections

fun <T> List<T>.toImmutableList(): List<T> {
    return Collections.unmodifiableList(this)
}