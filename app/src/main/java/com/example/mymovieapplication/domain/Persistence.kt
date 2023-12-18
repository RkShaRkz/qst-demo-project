package com.example.mymovieapplication.domain

/**
 * Class representing a DataSource, which is a backing information holder for [Repository] instances
 */
interface DataSource<T> {
    /**
     * Adds the [item] to the data source and persists it
     */
    fun saveItem(item: T)

    /**
     * Returns all saved data in this DataSource
     */
    fun getItems(): List<T>
}

/**
 * Generic class used for storage of [T] which is backed by a [DataSource] which is in charge of actually
 * persisting the data
 */
abstract class Repository<T>(private val dataSource: DataSource<T>) {
    internal fun addItem(item: T) {
        dataSource.saveItem(item)
    }
    internal fun getAllItems(): List<T> {
        return dataSource.getItems()
    }
}