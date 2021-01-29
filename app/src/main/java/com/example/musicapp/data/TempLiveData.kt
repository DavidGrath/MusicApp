package com.example.musicapp.data

/*
    Temporary implementation of Android Jetpack LiveData
    Implements the Observer pattern in the most basic way
 */
class TempLiveData<T>(var observable: T? = null) {
    fun update(newData: T?) {
        observable = newData
        observers.forEach {
            it.onDataChanged(newData)
        }
    }

    val observers: MutableList<TempObserver<T>> = ArrayList()
    fun addObserver(observer: TempObserver<T>) {
        observers.add(observer)
    }

    interface TempObserver<T> {
        fun onDataChanged(newData: T?)
    }
}